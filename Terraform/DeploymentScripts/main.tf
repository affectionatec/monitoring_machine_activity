terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 0.14.9"
}

provider "aws" {
  profile = "default"
  region  = "eu-west-2"
}

resource "aws_instance" "instance_1" {
  ami           = var.ami
  instance_type = "t2.micro"
  security_groups = [aws_security_group.service_security_group.name]
  key_name         = "ssh-key"
  user_data = file("./script.sh")

  tags = {
    Name = var.instance_name_1
  }
}

resource "aws_security_group" "service_security_group" {
  name = "service_security_group"
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

    ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_key_pair" "ssh-key" {
  key_name   = "ssh-key"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDUr4zvrDNm9Rie0dgIXMX9KSgC+h9dDgS7oYIC76ErDgyW4M7CyEagi9dc4szsDcgdTGO3PFLtvTzPUDg4tS/hPRdkp+VyBhmHhLjrwYVDwZbQoIl2W7c0ZLU1LwJhW42qPlYvdbTTTav9bPzRtHKR9Uy/f1Sr7tNUfhTJYBODJxq7xCwwRHqh3h5c48uMlxfS/gYn7q5dZc5Ym7wHbzk0Y7LDz/pP6ObrfL65iMD+UVodsw7XOBSwpSD+fdnWJKK56Fn1bmLt4cF3hglR8BvZ0NjyT2XMAHYVYkhi92tWfKL5fOvZ2JSsWt8FOzozuZ6OWlzfw/hQjG35OMGR/rXh caitlinyih@Caitlins-MacBook-Pro.local"
}