import scala.util.Random
import scala.io.StdIn.readLine

object Blackjack extends App {

  val rand = new Random
  var deck = List.range(1, 14).flatMap(value =>
    if (value == 1) List.fill(4)((value, "Ace"))
    else if (value > 10) List.fill(4)((10, List("Jack", "Queen", "King")(value - 11)))
    else List.fill(4)((value, value.toString)))

  def deal(deck: List[(Int, String)]): (List[(Int, String)], List[(Int, String)]) = {
    (deck.take(2), deck.drop(2))
  }

  def value(hand: List[(Int, String)]): Int = {
    val aces = hand.count(_._1 == 1)
    var total = hand.map(_._1).sum
    while (total <= 11 && aces > 0) {
      total += 10
    }
    total
  }

  def playHand(hand: List[(Int, String)], deck: List[(Int, String)]): (List[(Int, String)], List[(Int, String)]) = {
    val total = value(hand)
    if (total < 17) {
      println(s"Dealer hits.")
      playHand(deck.head :: hand, deck.tail)
    } else {
      (hand, deck)
    }
  }

  def play(balance: Int): Int = {
    if (balance <= 0) {
      println("You have no more money. Game over.")
      return balance
    }

    deck = Random.shuffle(deck)
    var bet = 0
    while (bet <= 0 || bet > balance) {
      bet = readLine(s"You have £$balance. How much do you want to bet? ").toInt
    }

    val (player, remainingDeck1) = deal(deck)
    var (dealer, remainingDeck2) = deal(remainingDeck1)

    println(s"Your cards are ${player.map(_._2).mkString(" and ")}. Total: ${value(player)}")
    println(s"Dealer's face-up card is ${dealer.head._2}")

    var hands = List(player)
    if (player(0) == player(1)) {
      if (readLine("Your cards are the same. Do you want to split (yes/no)? ") == "yes") {
        hands = List(List(player(0), remainingDeck2.head), List(player(1), remainingDeck2.tail.head))
        remainingDeck2 = remainingDeck2.tail.tail
      }
    }

    var results = List[Int]()
    for ((hand, index) <- hands.zipWithIndex) {
      var player = hand
      println(s"\nPlaying hand ${index + 1}")
      var input = ""
      while (input != "stand" && value(player) <= 21) {
        input = readLine("Do you want to hit or stand? ")
        if (input == "hit") {
          player = remainingDeck2.head :: player
          remainingDeck2 = remainingDeck2.tail
          println(s"Your cards are ${player.map(_._2).mkString(" and ")}. Total: ${value(player)}")
        }
      }

      if (value(player) > 21) {
        println("You busted.")
        results = results :+ -bet
      } else {
        println("Dealer reveals his card.")
        val (finalDealer, _) = playHand(dealer, remainingDeck2)
        println(s"Dealer's cards are ${finalDealer.map(_._2).mkString(" and ")}. Total: ${value(finalDealer)}")

        if (value(finalDealer) > 21 || value(player) > value(finalDealer)) {
          println("You win this hand!")
          results = results :+ bet
        } else if (value(player) < value(finalDealer)) {
          println("Dealer wins this hand.")
          results = results :+ -bet
        } else {
          println("This hand is a draw.")
        }
      }
    }

    val result = results.sum
    if (result < 0) {
      println(s"You lost £${-result} this round.")
    } else {
      println(s"You won £$result this round.")
    }

    if (readLine("Do you want to play again (yes/no)? ") == "yes") {
      play(balance + result)
    } else {
      balance + result
    }
  }

  val finalBalance = play(100)
  println(s"You leave the game with £$finalBalance.")
}
