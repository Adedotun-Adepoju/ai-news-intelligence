resource "alicloud_cr_repo" "repo" {
  namespace = "default"
  name      = "${var.project}-repo"
  summary   = "SWEN AI backend image"
}

resource "alicloud_cs_managed_kubernetes" "cluster" {
  name                  = "${var.project}-ack"
  worker_instance_types = ["ecs.g6.large"]
  num_of_nodes          = 2
}

output "ack_cluster_name" {
  value = alicloud_cs_managed_kubernetes.cluster.name
}
