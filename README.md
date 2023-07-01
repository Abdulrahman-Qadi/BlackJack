# Scala Blackjack

This is a command-line version of the game of Blackjack, written in Scala. 

## Features
- Play Blackjack against a computer dealer
- Option to place bets
- Option to split identical cards

## Prerequisites
- [Scala](https://www.scala-lang.org/download/) 2.13 or newer
- [sbt](https://www.scala-sbt.org/download.html) (Scala Build Tool)

## How to Play
1. Clone the repository: 
    ```bash
    git clone https://github.com/Abdulrahman-Qadi/BlackJack.git
    ```
2. Navigate into the repository: 
    ```bash
    cd your-repo
    ```
3. Run the game: 
    ```bash
    sbt run
    ```
4. Follow the prompts in the console to play the game!

## How the Game Works
- At the start of each round, you place a bet. The minimum bet is 1, and it cannot exceed your current balance.
- You're then dealt two cards, and you can choose to "hit" (get another card) or "stand" (stop getting cards).
- If your first two cards are identical, you can choose to "split" them into two separate hands and play them separately.
- The goal is to get as close to 21 as possible without going over. Aces can count as either 1 or 11, face cards count as 10, and all other cards are their numerical value.
- If you go over 21, you "bust" and lose your bet.
- If you don't bust, the dealer then plays. The dealer must hit if their total is 17 or less, and stand otherwise.
- If the dealer busts, you win your bet. If neither of you busts, whoever is closest to 21 wins. If there's a tie, you get your bet back.
- The game continues until you run out of money or choose to stop playing.

## Future Improvements
- Support for more advanced Blackjack rules, like double down and insurance
- Improve the dealer AI to make the game more challenging
- Implement a GUI to make the game more user-friendly

## License
This project is licensed under the MIT License.
