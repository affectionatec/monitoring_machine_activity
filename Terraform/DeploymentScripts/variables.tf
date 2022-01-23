variable "instance_name_1" {
  description = "Value of the Name tag for the EC2 instance"
  type        = string
  default     = "Service1"
}

variable "ami" {
    description = "Debian Buster 10 Amazon Machine Image"
    type        = string
    default     = "ami-050949f5d3aede071"
}