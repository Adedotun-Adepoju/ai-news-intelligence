variable "project_name" {
  type        = string
  description = "Name of the project"
  default     = "swen-ai-pipeline"
}

variable "aws_region" {
  type        = string
  description = "AWS region for deployment"
  default     = "us-east-1"
}

variable "alibaba_region" {
  type        = string
  description = "Alibaba Cloud region for deployment"
  default     = "ap-southeast-1"
}

variable "aws_access_key" {
  type        = string
  description = "AWS Access Key"
  sensitive   = true
}

variable "aws_secret_key" {
  type        = string
  description = "AWS Secret Key"
  sensitive   = true
}

variable "alibaba_access_key" {
  type        = string
  description = "Alibaba Cloud Access Key"
  sensitive   = true
}

variable "alibaba_secret_key" {
  type        = string
  description = "Alibaba Cloud Secret Key"
  sensitive   = true
}

variable "environment" {
  type        = string
  description = "Deployment environment"
  default     = "dev"
}
