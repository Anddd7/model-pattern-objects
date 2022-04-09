package `analysis-pattern`.accountability

import java.time.LocalDate

class Example28 {
    class AccountabilityType(val name: String)
    class EffectiveDateRange(val start: LocalDate, val end: LocalDate)

    interface Accountability {
        val type: AccountabilityType
        val effectiveDateRange: EffectiveDateRange
        val client: Party           // 甲方
        val committee: Party        // 乙方
    }

    interface Party {
        val accountabilities: List<Accountability>
    }
}

class Example29 {
    interface AccountabilityType {
        val name: String
        val allowableClients: List<PartyType>
        val allowableCommittee: List<PartyType>
    }

    class PartyType(val name: String)

    // 知识层
    // --------------------------------------------------
    // 操作层

    class EffectiveDateRange(val start: LocalDate, val end: LocalDate)

    interface Accountability {
        val type: AccountabilityType
        val effectiveDateRange: EffectiveDateRange
        val client: Party
        val committee: Party
        val activity: () -> Unit        // 活动 权责 条款

        fun constrain() {
            assert(client.type in type.allowableClients)
            assert(committee.type in type.allowableCommittee)
        }
    }

    interface Party {
        val type: PartyType
        val accountabilities: List<Accountability>
    }
}
