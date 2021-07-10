package com.nextskylineproject.onemore2048game;

public class Tile {
	public int x;
	public int y;
	public int value;
	
	public Tile(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public boolean equalsVal(Tile tile2) {
		return this.value == tile2.value;
	}
}
