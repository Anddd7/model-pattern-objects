package `analysis-pattern`.accountability

class Example21 {
    class PhoneNumber
    class Address
    class EmailAddress
    class Individual(val phoneNumber: PhoneNumber, val address: Address, val emailAddress: EmailAddress)
    class Company(val phoneNumber: PhoneNumber, val address: Address, val emailAddress: EmailAddress)
}

class Example22 {
    class PhoneNumber
    class Address
    class EmailAddress
    interface Party {
        val phoneNumber: PhoneNumber
        val address: Address
        val emailAddress: EmailAddress
    }

    sealed class Individual(
        override val phoneNumber: PhoneNumber,
        override val address: Address,
        override val emailAddress: EmailAddress
    ) : Party

    sealed class Company(
        override val phoneNumber: PhoneNumber,
        override val address: Address,
        override val emailAddress: EmailAddress
    ) : Party

    fun run(party: Party) {
        when (party) {
            is Individual -> {
                println("Individual: ${party.phoneNumber}")
                println("Individual: ${party.address}")
                println("Individual: ${party.emailAddress}")
            }
            is Company -> {
                println("Company: ${party.phoneNumber}")
                println("Company: ${party.address}")
                println("Company: ${party.emailAddress}")
            }
        }
    }
}
