output "cluster_name" {
  value = aws_eks_cluster.demo.name
}

output "endpoint" {
  value = aws_eks_cluster.demo.endpoint
}

output "cluster_ca_certificate" {
  value = aws_eks_cluster.demo.certificate_authority[0].data
}

output "kubeconfig" {
  value = <<EOT
apiVersion: v1
clusters:
- cluster:
    server: ${aws_eks_cluster.demo.endpoint}
    certificate-authority-data: ${aws_eks_cluster.demo.certificate_authority[0].data}
  name: ${aws_eks_cluster.demo.name}
contexts:
- context:
    cluster: ${aws_eks_cluster.demo.name}
    user: aws
  name: ${aws_eks_cluster.demo.name}
current-context: ${aws_eks_cluster.demo.name}
kind: Config
preferences: {}
users:
- name: aws
  user:
    exec:
      apiVersion: "client.authentication.k8s.io/v1beta1"
      command: "aws"
      args:
        - "eks"
        - "get-token"
        - "--region"
        - "${var.aws_region}"
        - "--cluster-name"
        - "${aws_eks_cluster.demo.name}"
EOT
} 