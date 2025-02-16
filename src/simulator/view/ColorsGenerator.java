package simulator.view;

import java.awt.Color;
import java.util.Random;

class ColorsGenerator {

	private final static long _DEFAULT_SEED = 9876543210l;

	private Random _rand;
	private long _seed;

	String[] _colors = { "0xFF0000", "0x00FF00", "0x0000FF", "0xFF00FF", "0x00FFFF", "0xFF6633", "0xFFB399", "0xFF33FF",
			"0xFFFF99", "0x00B3E6", "0xE6B333", "0x3366E6", "0x999966", "0x99FF99", "0xB34D4D", "0x80B300", "0x809900",
			"0xE6B3B3", "0x6680B3", "0x66991A", "0xFF99E6", "0xCCFF1A", "0xFF1A66", "0xE6331A", "0x33FFCC", "0x66994D",
			"0xB366CC", "0x4D8000", "0xB33300", "0xCC80CC", "0x66664D", "0x991AFF", "0xE666FF", "0x4DB3FF", "0x1AB399",
			"0xE666B3", "0x33991A", "0xCC9999", "0xB3B31A", "0x00E680", "0x4D8066", "0x809980", "0xE6FF80", "0x1AFF33",
			"0x999933", "0xFF3380", "0xCCCC00", "0x66E64D", "0x4D80CC", "0x9900B3", "0xE64D66", "0x4DB380", "0xFF4D4D",
			"0x99E6E6", "0x6666FF" };
	private int _nextColorIdx;

	ColorsGenerator() {
		this(_DEFAULT_SEED);
	}

	ColorsGenerator(long seed) {
		_rand = new Random(seed);
		_seed = seed;
		_nextColorIdx = 0;
	}

	void reset() {
		_rand = new Random(_seed);
		_nextColorIdx = 0;
	}

	Color nextColor() {
		if (_nextColorIdx < _colors.length) {
			return Color.decode(_colors[_nextColorIdx++]);
		} else
			return new Color(_rand.nextInt());
	}

}
