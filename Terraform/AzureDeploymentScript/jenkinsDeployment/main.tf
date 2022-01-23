# Configure the Microsoft Azure Provider
terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
      version = "2.46.0"
    }
  }
}

provider "azurerm" {
  features {}
  subscription_id   = "dc30b3bc-5acb-4caf-af32-d263f7b0634f"
  tenant_id         = "bdb74b30-9568-4856-bdbf-06759778fcbc"
  client_id         = "3cb1c133-6330-41ae-a05d-3eb4eaf38815"
  client_secret     = "o.kwQ-KSSgNqydKQyY~RUPS6yN3kFB4DHK"
}

# Create a resource group if it doesn't exist
resource "azurerm_resource_group" "myterraformgroup" {
    name     = "jenkinsResourceGroup"
    location = "uksouth"

    tags = {
        environment = "Terraform"
    }
}

# Create virtual network
resource "azurerm_virtual_network" "myterraformnetwork" {
    name                = "myVnet"
    address_space       = ["10.0.0.0/16"]
    location            = "uksouth"
    resource_group_name = azurerm_resource_group.myterraformgroup.name

    tags = {
        environment = "Terraform"
    }
}

# Create subnet
resource "azurerm_subnet" "myterraformsubnet" {
    name                 = "mySubnet"
    resource_group_name  = azurerm_resource_group.myterraformgroup.name
    virtual_network_name = azurerm_virtual_network.myterraformnetwork.name
    address_prefixes       = ["10.0.1.0/24"]
}

# Create public IPs
resource "azurerm_public_ip" "myterraformpublicip" {
    name                         = "myPublicIP"
    location                     = "uksouth"
    resource_group_name          = azurerm_resource_group.myterraformgroup.name
    allocation_method            = "Dynamic"

    tags = {
        environment = "Terraform"
    }
}

# Create Network Security Group and rule
resource "azurerm_network_security_group" "myterraformnsg" {
    name                = "myNetworkSecurityGroup"
    location            = "uksouth"
    resource_group_name = azurerm_resource_group.myterraformgroup.name

    security_rule {
        name                       = "SSH"
        priority                   = 1001
        direction                  = "Inbound"
        access                     = "Allow"
        protocol                   = "Tcp"
        source_port_range          = "*"
        destination_port_range     = "22"
        source_address_prefix      = "*"
        destination_address_prefix = "*"
    }


    security_rule {
    	name                       = "Port8080"
        priority                   = 310
        direction                  = "Inbound"
        access                     = "Allow"
        protocol                   = "*"
        source_port_range          = "*"
    	destination_port_range     = "8080"
    	source_address_prefix      = "*"
    	destination_address_prefix = "*"
  }


    security_rule {
    	name                       = "Port8081"
        priority                   = 1010
        direction                  = "Inbound"
        access                     = "Allow"
        protocol                   = "*"
        source_port_range          = "*"
    	destination_port_range     = "8081"
    	source_address_prefix      = "*"
    	destination_address_prefix = "*"
  }

    tags = {
        environment = "Terraform"
    }
}

# Create network interface
resource "azurerm_network_interface" "myterraformnic" {
    name                      = "myNIC"
    location                  = "uksouth"
    resource_group_name       = azurerm_resource_group.myterraformgroup.name

    ip_configuration {
        name                          = "myNicConfiguration"
        subnet_id                     = azurerm_subnet.myterraformsubnet.id
        private_ip_address_allocation = "Dynamic"
        public_ip_address_id          = azurerm_public_ip.myterraformpublicip.id
    }

    tags = {
        environment = "Terraform"
    }
}

# Connect the security group to the network interface
resource "azurerm_network_interface_security_group_association" "example" {
    network_interface_id      = azurerm_network_interface.myterraformnic.id
    network_security_group_id = azurerm_network_security_group.myterraformnsg.id
}



# Generate random text for a unique storage account name
resource "random_id" "randomId" {
    keepers = {
        # Generate a new ID only when a new resource group is defined
        resource_group = azurerm_resource_group.myterraformgroup.name
    }

   byte_length = 8
}

# Create storage account for boot diagnostics
resource "azurerm_storage_account" "mystorageaccount" {
    name                        = "diag${random_id.randomId.hex}"
    resource_group_name         = azurerm_resource_group.myterraformgroup.name
    location                    = "uksouth"
    account_tier                = "Standard"
    account_replication_type    = "LRS"

    tags = {
        environment = "Terraform"
    }
}

# Create (and display) an SSH key
#resource "tls_private_key" "example_ssh" {
#    name                        = "mypublickey"
#    resource_group_name         = azurerm_resource_group.myterraformgroup.name
#    location                    = "uksouth"
#}



# Create (and display) an SSH key
resource "tls_private_key" "example_ssh" {
     algorithm = "RSA"
     rsa_bits = 4096
}
output "tls_private_key" {
    value = tls_private_key.example_ssh.private_key_pem
    sensitive = true
}

# Create virtual machine
resource "azurerm_linux_virtual_machine" "myterraformvm" {
    name                  = "myVM"
    location              = "uksouth"
    resource_group_name   = azurerm_resource_group.myterraformgroup.name
    network_interface_ids = [azurerm_network_interface.myterraformnic.id]
    size                  = "Standard_B2s"

    os_disk {
        name              = "myOsDisk"
        caching           = "ReadWrite"
        storage_account_type = "Standard_LRS"
    }

    source_image_reference {
        publisher = "debian"
        offer     = "debian-10"
        sku       = "10"
        version   = "latest"
    }

    computer_name  = "jenkins-vm"
    admin_username = "zheliu"
    disable_password_authentication = true

    admin_ssh_key {
        username       = "zheliu"
        public_key     = tls_private_key.example_ssh.public_key_openssh
    }

    boot_diagnostics {
        storage_account_uri = azurerm_storage_account.mystorageaccount.primary_blob_endpoint
    }

    tags = {
        environment = "Terraform"
    }
}

#VM extension run some shell
resource "azurerm_virtual_machine_extension" "myextension" {
  	name                 = "jenkins-vm"
  	virtual_machine_id   = azurerm_linux_virtual_machine.myterraformvm.id
  	publisher            = "Microsoft.Azure.Extensions"
  	type                 = "CustomScript"
	type_handler_version = "2.0"

    settings = <<SETTINGS
    {
        "fileUris": ["https://zekestorageaccount.blob.core.windows.net/scripts/debian_deploy_script.sh"],
        "commandToExecute": "sh debian_deploy_script.sh"
    }
SETTINGS


  tags = {
    environment = "Production"
  }
}
