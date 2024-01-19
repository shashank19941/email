package email;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

public class MainAdvClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime minusDay1 = LocalDateTime.now().minusDays(1);
		// ArrayList<String> arr = new ArrayList<String>();
		long sumTknRequests = 0;
		long sumTknTokensIssued = 0;
		long sumTknDuplicate = 0;
		long sumDetknRequest = 0;
		long sumDetknResponse = 0;
		long sumJwtRequest = 0;
		long sumJwtResponse = 0;
		long sumCheckUID = 0;
		long sumUserError = 0;
		long sumAdvError = 0;
		long sumTotalRecord = 0;
		long sumRequestPending=0;
		String applicationNameData = args[0];
		String[] splitAppData = applicationNameData.split(" ");
		String[] splitData = null;

		boolean foundADVTESTPROD = true;
		// String[] Data = appData.split(" ");
		try {
			String html1 = "\r\n" + "<html>\r\n" + "<head>\r\n" + "    <title>Table Example</title>\r\n"
					+ "    <style>\r\n"
					+ "        table {border-collapse: collapse; width: 70%;max-width: 80%;font-family: sans-serif;font-size:12px; }\r\n"
					+ "        \r\n"
					+ "        th {border: 1px solid black;  padding: 8px; background-color: #E2DECC; color: black;}\r\n"
					+ "		td{text-align:right; background-color: #C0D4F7;border: 1px solid black;padding: 6px; }\r\n"
					+ "        \r\n" + "        tr:nth-child(even) {background-color: #f2f2f2; }\r\n" + "        \r\n"
					+ "		.textleft{text-align:left;}\r\n" + "    </style>\r\n" + "</head>\r\n" + "<body>\r\n"
					+ "    <table>\r\n" + "        <tr>\r\n" + "            <th>S.No.</th>\r\n"
					+ "            <th>Application Name</th>\r\n" + "            <th colspan='3'>Tokenization</th>\r\n"
					+ "				<th colspan='2'>Detokenization</th>\r\n" + " <th colspan='2'>JWT</th>\r\n"          +" <th>Check UID</th>\r\n" 
					+ "            <th colspan='2'>Errors</th>\r\n"
					+ "            <th>Response Pending</th>\r\n"
					+ "	<th>Total Records (Till Date)</th>\r\n" + "    	</tr>\r\n"
					+ "			<tr>\r\n" + "            <th></th>\r\n" + "            <th></th>\r\n"
					+ "            <th>Requests</th>\r\n" + "<th>Tokens Issued</th>\r\n"
					+ "			<th>Duplicate</th>\r\n" + "	<th>Request</th>\r\n"
					+ "			<th>Response</th>\r\n" + "<th>Request</th>\r\n"
					+ "			<th>Response</th>\r\n" + "<th></th>\r\n" + "			<th>(User End)</th>\r\n"
					+ "			<th>(ADV)</th>\r\n" + "	<th></th>\r\n" + " 	<th></th>\r\n" + "       </tr>\r\n";

			for (int i = 0; i < splitAppData.length; i++) {
				

				if (!splitAppData[i].contains("ADVTESTPROD")) {
					foundADVTESTPROD = false;
					int serialNumber = i;
					splitData = splitAppData[i].split("\\|");
					html1 = html1 + "<tr>\r\n" + "<td style='text-align:center;background-color:"
							+ ((i % 2 == 0) ? "#FDE9D9" : "#C0D4F7") + ";'>" + serialNumber + "</td>\r\n";
					
				
					for (int ii = 0; ii < splitData.length; ii++) {
						try {
							long value = Long.parseLong(splitData[ii]);
							// If the value is a string
							html1 = html1 + "<td  style='text-align:right;background-color: #FDE9D9;background-color:"
									+ ((i % 2 == 0) ? "#FDE9D9" : "#C0D4F7") + ";'>"
									+ formatIndianCommaSeparated(value);
							if (ii == 1) {
								sumTknRequests += value;
							}
							if (ii == 2) {
								sumTknTokensIssued += value;
							}
							if (ii == 3) {
								sumTknDuplicate += value;
							}
							if (ii == 4) {
								sumDetknRequest += value;
							}
							if (ii == 5) {
								sumDetknResponse += value;
							}
							if (ii == 6) {
								sumJwtRequest += value;
							}
							if (ii == 7) {
								sumJwtResponse += value;
							}
							if (ii == 8) {
								sumCheckUID += value;
							}
							if (ii == 9) {
								sumUserError += value;
							}
							if (ii == 10) {
								sumAdvError += value;
							}
							
							if (ii == 12) {
								sumTotalRecord += value;
							}

						} catch (NumberFormatException e) {
							html1 = html1
									+ "<td style='text-align:left;font-weight: bold;background-color: #FDE9D9;background-color:"
									+ ((i % 2 == 0) ? "#FDE9D9" : "#C0D4F7") + ";'>" + splitData[ii];
						}
						html1 = html1 + "</td>\r\n";
					}

				}
				if (foundADVTESTPROD) {
				}

				html1 = html1 + " </tr>\r\n";
			}
			sumRequestPending=(sumTknRequests-sumTknTokensIssued-sumTknDuplicate)+(sumDetknRequest-sumDetknResponse)+(sumJwtRequest-sumJwtResponse)-sumUserError-sumAdvError;
			html1 = html1 + "  <tr>\r\n" + "			<th colspan='2'>Total</th>\r\n"
					+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumTknRequests)
					+ "</th>\r\n" + "			<th style='text-align:right;'>"
					+ formatIndianCommaSeparated(sumTknTokensIssued) + "</th>\r\n"
					+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumTknDuplicate)
					+ "</th>\r\n" + "			<th style='text-align:right;'>"
					+ formatIndianCommaSeparated(sumDetknRequest) + "</th>\r\n"
					+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumDetknResponse)
					+ "</th>\r\n" + "			<th style='text-align:right;'>"
					+ formatIndianCommaSeparated(sumJwtRequest) + "</th>\r\n"
					+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumJwtResponse)
					+ "</th>\r\n" + "			<th style='text-align:right;'>"
					+ formatIndianCommaSeparated(sumCheckUID) + "</th>\r\n" + "			<th style='text-align:right;'>"
					+ formatIndianCommaSeparated(sumUserError) + "</th>\r\n"
					+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumAdvError) + "</th>\r\n"
							+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumRequestPending) + "</th>\r\n"
					+ "			<th style='text-align:right;'>" + formatIndianCommaSeparated(sumTotalRecord)
					+ "</th>\r\n" + "</tr>\r\n" + "  </table>\r\n" + "</body>\r\n" + "</html>";

			// Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();

			MailAuthSMTP mailAuthSMTP = new MailAuthSMTP();

			try {
				messageBodyPart.setContent("<label class='text'>Dear Sir,</label>" + "<br><br>"
						+ "<label class='text'>Details of transactions for Aadhaar Data Vault Platform for "
						+ dtf1.format(minusDay1) + ". </b></label><br><br>"
						+ "<label style='font-size:12px;font-style: italic;'>Note: Minor difference in number of Request and Response occurs when date changes before response is sent, as in case of requests received at around 11:59:59 pm and for which response may be sent by 12:00:00 am next day.<br><br></label>"
						+ html1
						+ "<br> Aadhaar data vault service dashboard (Live) can be accessed on- <a>https://nicca.nic.in/adv-dashboard/</a>"
						+ "<br><br>" + "<b>This is System Auto Generated Report</b></label>" + "<br>"
						+ "E-sign Data Management Service Division" + "<br>" + "National Informatics Centre",
						"text/html");
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				System.out.println("error in message body.................");
				e1.printStackTrace();
			}

		mailAuthSMTP.test1("sumeet@nic.in", messageBodyPart);
		//	mailAuthSMTP.test1("shashank.gupta83@gmail.com", messageBodyPart);
		} catch (Exception e) {
			System.out.println("error in sending mail...............");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String formatIndianCommaSeparated(long rupee) {
		// remove sign if present
		String raw = String.valueOf(Math.abs(rupee));
		int numDigits = raw.length();
		StringBuilder sb = new StringBuilder(raw);
		// Reverse the string to start from right most digits
		sb = sb.reverse();
		// Counter to keep track of number of commas placed
		int commas = 0;
		for (int i = 0; i < numDigits; i++) {
			// Insert a comma if i is in the range [3, 5, 7, 9, ...)
			if (i % 2 == 1 && i != 1) {
				sb.insert(i + commas, ",");
				commas++;
			} 
		}
		// Reverse the string back to get original number
		String sign = (rupee < 0) ? "-" : "";
		return sign + sb.reverse().toString();
	}

}
