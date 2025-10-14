terraform {
  required_version = ">= 1.6.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
    alicloud = {
      source  = "aliyun/alicloud"
      version = "~> 1.219.0"
    }
  }

  backend "s3" {
    bucket = "swen-terraform-state"
    key    = "state/global.tfstate"
    region = "us-east-1"
  }
}

provider "aws" {
  region = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

provider "alicloud" {
  region     = var.alibaba_region
  access_key = var.alibaba_access_key
  secret_key = var.alibaba_secret_key
}
