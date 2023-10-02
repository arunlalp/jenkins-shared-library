from checkov.common.models.enums import CheckResult, CheckCategories
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck

class EnsureTagsHaveValues(BaseResourceCheck):
    def __init__(self):
        name = "Ensure Tags Have Correct Values"
        id = "CUSTOM_AWS_111"
        supported_resources = (
            "aws_security_group", "aws_s3_bucket", "aws_dynamodb_table", "aws_instance",
            "aws_ecr_repository", "aws_iam_role", "aws_internet_gateway", "aws_route_table", 
            "aws_route", "aws_subnet", "aws_vpc",
        )

        categories = [CheckCategories.CONVENTION]
        guideline = "Ensure that the 'CostCenter' and 'Owner' tags have their respective values for all resources."
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources, guideline=guideline)

    def scan_resource_conf(self, conf) -> CheckResult:
        tags_list = conf.get("tags")
        
        cost_center_found = False
        owner_found = False

        if tags_list:
            for tags in tags_list:
                cost_center = tags.get("CostCenter")
                owner = tags.get("Owner")

                if cost_center == "techiescamp-commerce":
                    cost_center_found = True

                if owner == "techiescamp":
                    owner_found = True

        if cost_center_found and owner_found:
            return CheckResult.PASSED
        else:
            return CheckResult.FAILED

ensure_tags_have_values = EnsureTagsHaveValues()