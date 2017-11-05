package org.fnet.countdown;

public class Timer {

	private String name;
	private long timeSpanSeconds;

	public Timer(String name, long timeSpanSeconds) {
		this.name = name;
		this.timeSpanSeconds = timeSpanSeconds;
	}

	public String getName() {
		return name;
	}

	public long getTimeSpanSeconds() {
		return timeSpanSeconds;
	}

	public void setTimeSpanSeconds(long timeSpanSeconds) {
		this.timeSpanSeconds = timeSpanSeconds;
	}
	
	@Override
	public String toString() {
		return name + " (" + formatTimeSpan() + ")";
	}

	public String formatTimeSpan() {
		long seconds = timeSpanSeconds;
		long absSeconds = Math.abs(seconds);
		String positive = String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
		return seconds < 0 ? "-" + positive : positive;
	}
	
}
