variable "flavor" { default = "m1.large" }
variable "image" { default = "Debian Buster 10.3.0" }
#variable "instance" { default = "tf_instance" }

variable "name" { default = "monitoring-machine-activity" }


variable "network" { default = "MyNetwork" }   # you need to change this

variable "keypair" { default = "MyPublicKey" } # you need to change this
variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./deploy_script.sh" }
variable "security_description" { default = "Terraform security group" }
variable "security_name" { default = "security_deploy" }
