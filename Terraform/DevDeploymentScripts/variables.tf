variable "flavor" { default = "m1.medium" }
variable "image" { default = "Debian Buster 10.3.0" }
variable "name1" { default = "Instance 1" }
variable "name2" { default = "Instance 2" }

variable "keypair" { default = "caitlin_key" }
variable "network" { default = "caitlin_network" }

variable "pool" { default = "cscloud_private_floating" }
variable "script" { default = "./script.sh" }
variable "security_description" { default = "Application Security Group" }
variable "security_name" { default = "application_security_group" }