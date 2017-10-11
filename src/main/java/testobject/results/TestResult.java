package testobject.results;

public class TestResult
{
	public String sessionId;
	public TestStatus status;
	
	public TestResult(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	
	/** get Status **/
	public TestStatus getStatus()
	{
		return status;
	}
	
	public boolean passed()
	{
		System.out.println("STATUS: " + status + " : " + (status == TestStatus.PASSED));
		return status == TestStatus.PASSED;
	}
	
	public boolean failed()
	{
		System.out.println("STATUS: " + status + " : " + (status == TestStatus.PASSED));
		
		return status == TestStatus.FAILED;
	}
	
	
	
	/** Set Status **/
	public void setStatus(TestStatus status)
	{
		this.status = status;
	}
	
	public void setStatus(String status)
	{
		this.status = TestStatus.valueOf(status);
	}
	
	public void setStatus(boolean status)
	{
		this.status = status ? TestStatus.PASSED : TestStatus.FAILED;
	}
	
	public void pass()
	{
		status = TestStatus.PASSED;
	}
	
	public void fail()
	{
		status = TestStatus.FAILED;
	}
}
