package solvers

const val cards = "23456789TJQKA"
const val cardsWithJoker = "J23456789TQKA"

fun getCardStrength(card: Char): Int {
    return cards.indexOf(card)
}

fun getCardStrengthWithJoker(card: Char): Int {
    return cardsWithJoker.indexOf(card)
}

open class Hand(val cards: String, val bid: Long) : Comparable<Hand> {

    open fun getHandType(hand: String): Int {
        val cardFrequencies = HashMap<Char, Int>()
        for (card in hand) {
            if (card !in cardFrequencies) {
                cardFrequencies[card] = 1
            } else {
                cardFrequencies[card] = cardFrequencies[card]!!.plus(1)
            }
        }
        val sortedFrequencies = cardFrequencies.values.toMutableList().sortedDescending()
        if (sortedFrequencies[0] == 1) {
            return 1
        } else if (sortedFrequencies[0] == 2) {
            return if (sortedFrequencies[1] != 2) {
                2
            } else {
                3
            }
        } else if (sortedFrequencies[0] == 3) {
            return if (sortedFrequencies[1] != 2) {
                4
            } else {
                5
            }
        } else if (sortedFrequencies[0] == 4) {
            return 6
        } else {
            return 7
        }
    }

    override fun compareTo(other: Hand): Int {
        val cards1 = cards
        val cards2 = other.cards
        val type1 = getHandType(cards1)
        val type2 = getHandType(cards2)
        if (type1 != type2) {
            return if (type1 > type2) 1 else -1
        } else {
            for (i in cards1.indices) {
                if (getCardStrength(cards1[i]) > getCardStrength(cards2[i])) {
                    return 1
                } else if (getCardStrength(cards1[i]) < getCardStrength(cards2[i])) {
                    return  -1
                }
            }
        }
        return 0
    }

    override fun toString(): String {
        return "Cards: $cards - Bid: $bid"
    }
}

class JokerHand(cards: String, bid: Long) : Hand(cards, bid) {
    override fun getHandType(hand: String): Int {
        val cardFrequencies = HashMap<Char, Int>()
        val jokers: Int = hand.count{it == 'J'}
        if (jokers >= 4)
            return 7
        val jokerlessHand = hand.replace("J", "")
        for (card in jokerlessHand) {
            if (card !in cardFrequencies) {
                cardFrequencies[card] = 1
            } else {
                cardFrequencies[card] = cardFrequencies[card]!!.plus(1)
            }
        }
        val sortedFrequencies = cardFrequencies.values.toMutableList().sortedDescending()
        if (sortedFrequencies[0] + jokers == 1) {
            return 1
        } else if (sortedFrequencies[0] + jokers == 2) {
            return if (sortedFrequencies[1] != 2) {
                2
            } else {
                3
            }
        } else if (sortedFrequencies[0] + jokers == 3) {
            return if (sortedFrequencies[1] != 2) {
                4
            } else {
                5
            }
        } else if (sortedFrequencies[0] + jokers == 4) {
            return 6
        } else {
            return 7
        }
    }

    override fun compareTo(other: Hand): Int {
        val cards1 = cards
        val cards2 = other.cards
        val type1 = getHandType(cards1)
        val type2 = getHandType(cards2)
        if (type1 != type2) {
            return if (type1 > type2) 1 else -1
        } else {
            for (i in cards1.indices) {
                if (getCardStrengthWithJoker(cards1[i]) > getCardStrengthWithJoker(cards2[i])) {
                    return 1
                } else if (getCardStrengthWithJoker(cards1[i]) < getCardStrengthWithJoker(cards2[i])) {
                    return  -1
                }
            }
        }
        return 0
    }

}

class DaySevenSolver(inputPath: String) : DaySolver(inputPath) {
    override fun solvePart1(): String {
        val hands = ArrayList<Hand>()
        var totalWinnings: Long = 0
        for (line in input) {
            val cards = line.split(" ")[0]
            val bid = line.split(" ")[1].toLong()
            hands.add(Hand(cards, bid))
        }
        hands.sort()
        for (i in hands.indices) {
            totalWinnings += hands[i].bid * (i + 1)
        }
        return totalWinnings.toString()
    }

    override fun solvePart2(): String {
        val hands = ArrayList<JokerHand>()
        var totalWinnings: Long = 0
        for (line in input) {
            val cards = line.split(" ")[0]
            val bid = line.split(" ")[1].toLong()
            hands.add(JokerHand(cards, bid))
        }
        hands.sort()
        for (i in hands.indices) {
            totalWinnings += hands[i].bid * (i + 1)
        }
        return totalWinnings.toString()
    }
}