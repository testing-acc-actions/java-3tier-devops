# Java 3-Tier Application on AWS

A beginner-friendly AWS DevOps project that deploys a Java Spring Boot
application using Terraform and GitHub Actions.

## Project Objectives

- Build a Java Spring Boot application.
- Understand three-tier architecture.
- Provision AWS infrastructure using Terraform.
- Automate infrastructure deployment using GitHub Actions.
- Configure secure AWS authentication using GitHub OIDC.
- Implement monitoring, security and cost optimisation.

## Architecture

The project contains three main tiers:

1. Presentation Tier - Application Load Balancer.
2. Application Tier - Java Spring Boot application on EC2.
3. Database Tier - Amazon RDS.

## Technology Stack

- Java
- Spring Boot
- AWS
- Terraform
- GitHub Actions
- Amazon VPC
- Amazon EC2
- Application Load Balancer
- Amazon RDS
- Amazon S3
- Amazon CloudWatch

## AWS Region

Asia Pacific (Mumbai) - ap-south-1

## Security

- IAM least privilege.
- GitHub Actions OIDC authentication.
- Private application and database subnets.
- Security group restrictions.
- Encrypted Terraform remote state.

## Cost Optimisation

- Avoid unnecessary NAT Gateway costs.
- Start with minimal infrastructure.
- Review AWS pricing before deployment.
- Clean up resources after exercises.

## Project Status

In development.
