/*******************************************************************************
 * File Name:		FlappyPong.java
 * Project:			Flappy Pong Game
 * 
 * Designer:		Garrett Cross
 * 
 * Copyright © 2019 Garrett Cross. All rights reserved.
 ******************************************************************************/
package flappyPong;

import processing.core.*;

/**
 * <tt>FlappyPong</tt>: A game based on a tutorial by
 * <A HREF="https://www.toptal.com/resume /oguz-gelal">Oguz Gelal</A>.
 * <P>
 * Tutorial found <A HREF="https://www.toptal.com/game/
 * ultimate-guide-to-processing-simple-game">here</A>.
 * <P>
 * This game was made in Eclipse, and some changes have been made from the
 * original design of the game. This is not an exact duplicate of the tutorial
 * game.
 * 
 * @author Garrett Cross
 * @version 0.1.1
 * @since 10/04/2019
 * 
 */
public class FlappyPong extends PApplet
{

    public static void main(String[] args)
    {
        // Creates the Processing.org Applet
        PApplet.main("flappyPong.FlappyPong");
    }

    /***************************************************************************
     * VARIABLES
     **************************************************************************/

    // We control which screen is active by settings / updating
    // gameScreen variable. We display the correct screen according
    // to the value of this variable.
    //
    // 0: Initial Screen
    // 1: Game Screen
    // 2: Game-over Screen

    int gameScreen = 0;

    float ballX, ballY;             // ball x-coord and y-coord
    int ballSize = 20;              // ball size
    int ballColor = color(0);       // ball color

    float gravity = 1;              // gravity variable
    float ballSpeedVert = 0;        // ball vertical speed

    double airFriction = 0.0001;    // friction from ball moving through air
    double friction = 0.1;          // friction from ball hitting surface

    int racketColor = color(0);     // racket color
    float racketWidth = 100;        // racket width
    float racketHeight = 10;        // racket height

    /***************************************************************************
     * SETTINGS BLOCK
     **************************************************************************/

    public void settings()
    {
        size(500, 500);
    }

    /***************************************************************************
     * SETUP BLOCK
     **************************************************************************/

    public void setup()
    {
        ballX = width / 4;
        ballY = height / 5;
    }

    /***************************************************************************
     * DRAW BLOCK
     **************************************************************************/

    public void draw()
    {
        // Displays the contents of the current screen
        if (gameScreen == 0)
        {
            initScreen();
        } else if (gameScreen == 1)
        {
            gameScreen();
        } else if (gameScreen == 2)
        {
            gameOverScreen();
        }
    }

    /***************************************************************************
     * SCREEN CONTENTS
     **************************************************************************/

    public void initScreen()
    { // codes of initial screen
        background(0);
        textAlign(CENTER);
        text("Click anywhere to start", height / 2, width / 2);
    }

    public void gameScreen()
    { // codes of game screen
        background(255);

        drawBall();
        applyGravity();
        keepOnScreen();

        drawRacket();
        watchRacketBounce();
    }

    public void gameOverScreen()
    { // codes of game-over screen

    }

    /***************************************************************************
     * INPUTS
     **************************************************************************/

    public void mousePressed()
    {
        // if on the initial screen when click, start the game
        if (gameScreen == 0)
        {
            startGame();
        }
    }

    /***************************************************************************
     * OTHER FUNCTIONS
     **************************************************************************/

    // Sets the necessary variables to start the game
    public void startGame()
    {
        gameScreen = 1;
    }

    // Draws the ball
    public void drawBall()
    {
        fill(ballColor);
        ellipse(ballX, ballY, ballSize, ballSize);
    }

    // Applies gravity to the ball
    public void applyGravity()
    {
        ballSpeedVert += gravity;
        ballSpeedVert -= (ballSpeedVert * airFriction);
        ballY += ballSpeedVert;
    }

    // Makes ball bounce off of bottom of screen
    public void makeBounceBottom(float surface)
    {
        ballY = surface - (ballSize / 2);
        ballSpeedVert *= -1;
        ballSpeedVert -= (ballSpeedVert * friction);
    }

    // Makes ball bounce off of top of screen
    public void makeBounceTop(float surface)
    {
        ballY = surface + (ballSize / 2);
        ballSpeedVert *= -1;
        ballSpeedVert -= (ballSpeedVert * friction);
    }

    // Keeps the ball on screen
    public void keepOnScreen()
    {
        // ball hits floor
        if (ballY + (ballSize / 2) > height)
        {
            makeBounceBottom(height);
        }

        if (ballY - (ballSize / 2) < 0)
        {
            makeBounceTop(0);
        }
    }

    // Draws the racket
    public void drawRacket()
    {
        fill(racketColor);
        rectMode(CENTER);
        rect(mouseX, mouseY, racketWidth, racketHeight);
    }

    // Makes ball bounce off of racket
    public void watchRacketBounce()
    {
        float overhead = mouseY - pmouseY;
        if ((ballX + (ballSize / 2) > mouseX - (racketWidth / 2))
                && (ballX - (ballSize / 2) < mouseX + (racketWidth / 2)))
        {
            if (dist(ballX, ballY, ballX, mouseY) <= (ballSize / 2) + abs(overhead))
            {
                makeBounceBottom(mouseY);

                // racket moving up
                if (overhead < 0)
                {
                    ballY += overhead;
                    ballSpeedVert += overhead;
                }
            }
        }
    }
}
