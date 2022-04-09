package `analysis-pattern`.accountability

import java.time.LocalDate

class Example26 {
    class OrganizationType(val name: String)
    class EffectiveDateRange(val start: LocalDate, val end: LocalDate)

    // 即以有向图的方式进行建模, 组织为点, 结构为线
    interface OrganizationStructure {
        val type: OrganizationType
        val effectiveDateRange: EffectiveDateRange
        val parent: Organization
        val child: Organization

        fun constraint() {
            // rule 1
            if (type.name == "销售组织")
                if (child is Department)
                    assert(parent is Region)
            // rule 2
            // rule ...
        }
    }

    interface Organization {
        val structures: List<OrganizationStructure>

        fun constraints() {
            structures.forEach { it.constraint() }
        }
    }

    class Agency(override val structures: List<OrganizationStructure>) : Organization
    class Department(override val structures: List<OrganizationStructure>) : Organization
    class Region(override val structures: List<OrganizationStructure>) : Organization
    class BusinessUnit(override val structures: List<OrganizationStructure>) : Organization
}

class Example27 {
    interface OrganizationType {
        val name: String
        val rule: OrganizationRule
    }

    interface OrganizationRule {
        fun constraint(structure: OrganizationStructure)
    }

    class EffectiveDateRange(val start: LocalDate, val end: LocalDate)

    interface OrganizationStructure {
        val type: OrganizationType
        val effectiveDateRange: EffectiveDateRange
        val parent: Organization
        val child: Organization
    }

    interface Organization {
        val structures: List<OrganizationStructure>

        fun constraints() {
            structures.forEach { it.type.rule.constraint(it) }
        }
    }

    class Agency(override val structures: List<OrganizationStructure>) : Organization
    class Department(override val structures: List<OrganizationStructure>) : Organization
    class Region(override val structures: List<OrganizationStructure>) : Organization
    class BusinessUnit(override val structures: List<OrganizationStructure>) : Organization

    // 动态增加新的组织类型
    class SalesOrganizationType : OrganizationType {
        override val name: String = "销售组织"
        override val rule: OrganizationRule = DepartmentMustReportToRegionRule
    }

    class ProductOrganizationType : OrganizationType {
        override val name: String = "产品组织"
        override val rule: OrganizationRule = DepartmentMustReportToRegionRule
    }

    // 自定义的可复用的约束规则
    object DepartmentMustReportToRegionRule : OrganizationRule {
        override fun constraint(structure: OrganizationStructure) {
            if (structure.child is Department)
                assert(structure.parent is Region)
        }
    }
}
