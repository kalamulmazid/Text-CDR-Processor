package com.processor.Text_CDR_Processor;

public class CDRHashMapProcessor implements Runnable {

	private CDR cdrObject;

	public CDRHashMapProcessor(CDR cdrObject) {
		this.cdrObject = cdrObject;
	}
	
	@Override
	public void run() {
		System.out.println("Starting Processing CDR of Calling Party : " + cdrObject.getCallingParty());
		try {
			BonusCalculator(cdrObject);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed Processing CDR of Calling Party : " + cdrObject.getCallingParty());
	}
	
	private void BonusCalculator(CDR cdrObject){
		if(cdrObject.getCallDuration()>400)
		{
			System.out.println(cdrObject.getCallingParty() +" is eligible for Bonus");
		}
		else
		{
			System.out.println(cdrObject.getCallingParty() +" is not eligible for Bonus");
		}
	}

}
