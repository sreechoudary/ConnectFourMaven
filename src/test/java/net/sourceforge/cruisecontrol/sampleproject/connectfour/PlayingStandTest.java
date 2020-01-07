/********************************************************************************
 * CruiseControl, a Continuous Integration Toolkit
 * Copyright (c) 2005, ThoughtWorks, Inc.
 * 200 E. Randolph, 25th Floor
 * Chicago, IL 60601 USA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     + Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     + Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 *     + Neither the name of ThoughtWorks, Inc., CruiseControl, nor the
 *       names of its contributors may be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ********************************************************************************/
package net.sourceforge.cruisecontrol.sampleproject.connectfour;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayingStandTest {

    @Test
	public void testFourConnected() throws GameOverException {
        PlayingStand stand = new PlayingStand();
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(0);
        stand.dropBlack(6);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(0);
        stand.dropBlack(6);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(0);
        stand.dropBlack(6);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(0);
        Assert.assertTrue(stand.areFourConnected());

        try {
            stand.dropBlack(6);
            Assert.fail("Game is over, should be an exception");
        } catch (GameOverException expected) {
        }
    }

    @Test
	public void testFourConnectedHorizontally() throws GameOverException {
        PlayingStand stand = new PlayingStand();
        createRedWinsHorizontally(stand);
        Assert.assertTrue(stand.areFourConnected());

        try {
            stand.dropBlack(6);
            Assert.fail("Game is over, should be an exception");
        } catch (GameOverException expected) {
        }
    }

    private void createRedWinsHorizontally(PlayingStand stand) {
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(0);
        stand.dropBlack(6);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(1);
        stand.dropBlack(6);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(2);
        stand.dropBlack(6);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(3);
    }

    @Test
	public void testFourConnectedDiagonally() throws GameOverException {
        PlayingStand stand = new PlayingStand();
        createRedWinsDiagonallyUpward(stand);
        Assert.assertTrue(stand.areFourConnected());

        try {
            stand.dropBlack(6);
            Assert.fail("Game is over, should be an exception");
        } catch (GameOverException expected) {
        }

        Assert.assertEquals(Chip.RED, stand.getWinner());
    }

    @Test
	public void testStandControlsTurns() throws GameOverException {
        PlayingStand stand = new PlayingStand();
        stand.dropRed(1);
        try {
            stand.dropRed(1);
            Assert.fail("Expected an exception");
        } catch (OutOfTurnException expected) {
        }

        stand.dropBlack(1);
        try {
            stand.dropBlack(1);
            Assert.fail("Expected an exception");
        } catch (OutOfTurnException expected) {
        }

        stand.dropRed(1);
        stand.dropBlack(2);
    }

    @Test
	public void testFullColumn() {
        PlayingStand stand = new PlayingStand();
        stand.dropRed(0);
        stand.dropBlack(0);
        stand.dropRed(0);
        stand.dropBlack(0);
        stand.dropRed(0);
        stand.dropBlack(0);

        try {
            stand.dropRed(0);
            Assert.fail("Expected an exception");
        } catch (FullColumnException expected) {
        }
    }

    @Test
	public void testNonExistentColumn() {
        PlayingStand stand = new PlayingStand();
        try {
            stand.dropRed(-1);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }

        try {
            stand.dropBlack(-1);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }

        try {
            stand.dropRed(7);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }

        try {
            stand.dropRed(8);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }

        try {
            stand.dropRed(10000);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }

        try {
            stand.dropRed(Integer.MAX_VALUE);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }

        try {
            stand.dropRed(Integer.MIN_VALUE);
            Assert.fail("Expected an exception");
        } catch (InvalidColumnException expected) {
        }
    }

    @Test
	public void testNoWinner() {
        PlayingStand stand = new PlayingStand();

        fillWholeStandWithoutWinner(stand);

        Assert.assertFalse(stand.areFourConnected());
        Assert.assertTrue(stand.isGameOver());
        Assert.assertNull(stand.getWinner());

        try {
            stand.dropRed(0);
            Assert.fail("Expected an exception");
        } catch (GameOverException expected) {
        }
    }

    /**
     * 5|
     * 4|
     * 3| R
     * 2| B R
     * 1| R B R
     * 0| B R B R B
     * --------------
     * 0 1 2 3 4 5 6
     */
	@Test
    public void testDownwardDiagonalWins() {
        PlayingStand stand = new PlayingStand();
        stand.dropRed(3);
        stand.dropBlack(2);
        stand.dropRed(2);
        stand.dropBlack(0);
        stand.dropRed(1);
        stand.dropBlack(1);
        stand.dropRed(1);
        stand.dropBlack(4);
        stand.dropRed(0);
        stand.dropBlack(0);
        stand.dropRed(0);

        Assert.assertTrue(stand.areFourConnected());
        Assert.assertTrue(stand.isGameOver());
    }

    @Test
	public void testWinningPlacement() {
        PlayingStand stand = new PlayingStand();
        createRedWinsDiagonallyUpward(stand);

        PlayingStand.WinningPlacement placement = stand.getWinningPlacement();
        Assert.assertNotNull(placement);

        Cell startCell = placement.getStartingCell();
        Assert.assertEquals(0, startCell.getColumn());
        Assert.assertEquals(0, startCell.getRow());
        Assert.assertEquals(Direction.UPWARD_DIAGONAL, placement.getDirection());

        stand = new PlayingStand();
        createRedWinsHorizontally(stand);

        placement = stand.getWinningPlacement();
        Assert.assertEquals(0, placement.getStartingCell().getColumn());
        Assert.assertEquals(0, placement.getStartingCell().getRow());
        Assert.assertEquals(Direction.HORIZONTAL, placement.getDirection());
    }

    @Test
	public void testNoWinningPlacementBeforeGameOver() {
        PlayingStand stand = new PlayingStand();

        try {
            stand.getWinningPlacement();
            Assert.fail("Expected an exception");
        } catch (GameNotOverException expected) {
        }

        fillWholeStandWithoutWinner(stand);
        try {
            stand.getWinningPlacement();
            Assert.fail("Expected an exception");
        } catch (StalemateException expected) {
        }
    }

    private void createRedWinsDiagonallyUpward(PlayingStand stand) {
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(0);
        stand.dropBlack(1);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(1);
        stand.dropBlack(2);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropRed(2);
        stand.dropBlack(3);
        stand.dropRed(2);
        Assert.assertFalse(stand.areFourConnected());

        stand.dropBlack(5);
        stand.dropRed(3);
        stand.dropBlack(3);
        stand.dropRed(3);
    }

    private void fillWholeStandWithoutWinner(PlayingStand stand) {
        stand.dropRed(0);
        stand.dropBlack(1);
        stand.dropRed(0);
        stand.dropBlack(1);
        stand.dropRed(0);
        stand.dropBlack(1);

        stand.dropRed(1);
        stand.dropBlack(0);
        stand.dropRed(1);
        stand.dropBlack(0);
        stand.dropRed(1);
        stand.dropBlack(0);

        stand.dropRed(2);
        stand.dropBlack(3);
        stand.dropRed(2);
        stand.dropBlack(3);
        stand.dropRed(2);
        stand.dropBlack(3);

        stand.dropRed(3);
        stand.dropBlack(2);
        stand.dropRed(3);
        stand.dropBlack(2);
        stand.dropRed(3);
        stand.dropBlack(2);

        stand.dropRed(4);
        stand.dropBlack(5);
        stand.dropRed(4);
        stand.dropBlack(5);
        stand.dropRed(4);
        stand.dropBlack(5);

        stand.dropRed(5);
        stand.dropBlack(4);
        stand.dropRed(5);
        stand.dropBlack(4);
        stand.dropRed(5);
        stand.dropBlack(4);

        stand.dropRed(6);
        stand.dropBlack(6);
        stand.dropRed(6);
        stand.dropBlack(6);
        stand.dropRed(6);
        stand.dropBlack(6);
    }
}
