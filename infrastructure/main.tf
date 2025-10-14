locals {
  use_aws     = true
  use_alibaba = false
}

# AWS Deployment
module "aws_backend" {
  count   = local.use_aws ? 1 : 0
  source  = "./modules/aws"
  project = var.project_name
  region  = var.aws_region
}

# Alibaba Deployment
module "alibaba_backend" {
  count   = local.use_alibaba ? 1 : 0
  source  = "./modules/alibaba"
  project = var.project_name
  region  = var.alibaba_region
}
