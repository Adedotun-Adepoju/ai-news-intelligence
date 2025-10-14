variable "project" {}
variable "region" {}
variable "eks_role_arn" {}
variable "subnet_ids" {
  type = list(string)
  default = []
}
