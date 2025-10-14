resource "aws_ecr_repository" "repo" {
  name = "${var.project}-repo"
}

resource "aws_eks_cluster" "cluster" {
  name     = "${var.project}-cluster"
  role_arn = var.eks_role_arn

  vpc_config {
    subnet_ids = var.subnet_ids
  }
}

resource "null_resource" "deploy_k8s" {
  provisioner "local-exec" {
    command = "kubectl apply -f ../../k8s/"
  }
  depends_on = [aws_eks_cluster.cluster]
}

output "cluster_name" {
  value = aws_eks_cluster.cluster.name
}
