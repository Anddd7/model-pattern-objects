package `analysis-pattern`.accountability

class Example23 {
    class Agency
    class Department(val agencies: List<Agency>)
    class Region(val departments: List<Department>)
    class BusinessUnit(val regions: List<Region>)
}

class Example24 {
    interface Organization {
        val parent: Organization?
        val children: List<Organization>
    }

    sealed class Agency(override val parent: Department, override val children: List<Organization>) : Organization
    sealed class Department(override val parent: Region, override val children: List<Organization>) : Organization
    sealed class Region(override val parent: BusinessUnit, override val children: List<Organization>) : Organization
    sealed class BusinessUnit(override val children: List<Organization>) : Organization {
        override val parent: Organization? = null
    }
}

class Example25 {
    interface ProductOrganization {
        val productParent: ProductOrganization?
        val productChildren: List<ProductOrganization>
    }

    interface MarketingOrganization {
        val marketingParent: MarketingOrganization?
        val marketingChildren: List<MarketingOrganization>
    }

    interface Organization : ProductOrganization, MarketingOrganization

    // 产品部门只汇报给管理线, 并管理下面的产品团队
    class ProductTeamA(
        override val productParent: ProductOrganization?,
        override val productChildren: List<ProductOrganization>
    ) : ProductOrganization

    // 销售团队不仅要汇报给地区的销售部门, 还要汇报给相应的产品部门
    class SalesTeamA(
        override val productParent: ProductOrganization?,
        override val productChildren: List<ProductOrganization>,
        override val marketingParent: MarketingOrganization?,
    ) : Organization {
        override val marketingChildren: List<MarketingOrganization> = emptyList()
    }
}
