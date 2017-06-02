package com.processor.Text_CDR_Processor;

class CDR {
	private String callingParty;
	private String calledParty;
	private int callDuration;
	
	String getCallingParty() {
		return callingParty;
	}
	void setCallingParty(String callingParty) {
		this.callingParty = callingParty;
	}
	String getCalledParty() {
		return calledParty;
	}
	void setCalledParty(String calledParty) {
		this.calledParty = calledParty;
	}
	int getCallDuration() {
		return callDuration;
	}
	void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}
	
	@Override
	public String toString() {
		return "CDR [callingParty=" + callingParty + ", calledParty=" + calledParty + ", callDuration=" + callDuration
				+ "]";
	}
}
