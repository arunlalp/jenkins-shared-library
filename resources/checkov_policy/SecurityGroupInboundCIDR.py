from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck
from checkov.common.models.enums import CheckResult, CheckCategories

class NonPublicCidrBlockCheck(BaseResourceCheck):
    def __init__(self) -> None:
        name = "Ensure AWS security groups have non-public CIDR blocks"
        id = "CUSTOM_AWS_002"
        supported_resources = ("aws_security_group",)
        categories = (CheckCategories.NETWORKING,)
        guideline = "CIDR blocks in security group rules should not be set to 0.0.0.0/0."
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources, guideline=guideline)

    def scan_resource_conf(self, conf) -> CheckResult:
       
        ingress_rules = conf.get("ingress")
        if ingress_rules:
            for rule in ingress_rules:
                cidr_blocks = rule.get("cidr_blocks")
                if cidr_blocks and any("0.0.0.0/0" in block for block in cidr_blocks):
                    return CheckResult.FAILED
        return CheckResult.PASSED

non_public_cidr_check = NonPublicCidrBlockCheck()