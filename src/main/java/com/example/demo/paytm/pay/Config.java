package com.example.demo.paytm.pay;

//import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.paytm.pg.merchant.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;


public class Config {

	public final static String PAYTM_MID="eoWwSj13188372679403";
	public final static String PAYTM_MERCHANT_KEY="8dinf#3aB8awxX7k";
	public final static String PAYTM_WEBSITE="WEBSTAGING";
	//Replace base url on basis of your site
	public final static String PAYTM_CALLBACK_URL="http://localhost:8080/jscheckoutjava/callback";
	// for staging
	public final static String PAYTM_ENVIRONMENT="https://securegw-stage.paytm.in";

	// for production
	// public final static String PAYTM_ENVIRONMENT="https://securegw.paytm.in";

	public static Map<String, Object> getTransactionToken() throws Exception {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String generatedOrderID = "PYTM_BLINK_"+timestamp.getTime();
		double amount = 1.00;

		/* initialize an object */
		JSONObject paytmParams = new JSONObject();


		/* body parameters */
		JSONObject body = new JSONObject();

		/* for custom checkout value is 'Payment' and for intelligent router is 'UNI_PAY' */
		body.put("requestType","Payment");

		/* Find your MID in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
		body.put("mid", PAYTM_MID);

		/* Find your Website Name in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
		body.put("websiteName", PAYTM_WEBSITE);

		/* Enter your unique order id */
		body.put("orderId", generatedOrderID);

		/* on completion of transaction, we will send you the response on this URL */
		body.put("callbackUrl", PAYTM_CALLBACK_URL);

		/* initialize an object for txnAmount */
		JSONObject txnAmount = new JSONObject();

		/* Transaction Amount Value */
		txnAmount.put("value", amount);

		/* Transaction Amount Currency */
		txnAmount.put("currency", "INR");

		/* initialize an object for userInfo */
		JSONObject userInfo = new JSONObject();

		/* unique id that belongs to your customer */
		userInfo.put("custId", "cust_"+timestamp.getTime());

		/* put txnAmount object in body */
		body.put("txnAmount", txnAmount);

		/* put userInfo object in body */
		body.put("userInfo", userInfo);

		/**
		 * Generate checksum by parameters we have in body
		 * You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		 * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys 
		 */

		// String checksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.PAYTM_MERCHANT_KEY, body.toString());
		String checksum = PaytmChecksum.generateSignature(body.toString(),PAYTM_MERCHANT_KEY);
		/* head parameters */
		JSONObject head = new JSONObject();

		/* put generated checksum value here */
		head.put("signature", checksum);

		/* prepare JSON string for request */
		paytmParams.put("body", body);
		paytmParams.put("head", head);
		String post_data = paytmParams.toString();

		URL url = new URL(PAYTM_ENVIRONMENT+"/theia/api/v1/initiateTransaction?mid="+PAYTM_MID+"&orderId="+generatedOrderID);

		String responseData = "";
		/* result parameters */

		Map<String, Object> resultdata =  new HashMap<>();

		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			requestWriter.writeBytes(post_data);
			requestWriter.close();

			InputStream is = connection.getInputStream();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			if ((responseData = responseReader.readLine()) != null) {
				//System.out.append("Response: " + responseData);
				//String jsonString = responseData ; //assign your JSON String here
				JSONObject obj = new JSONObject(responseData);
				String txnToken = obj.getJSONObject("body").getString("txnToken");
				JSONObject bodyres = obj.getJSONObject("body");
				String resultstatus = bodyres.getJSONObject("resultInfo").getString("resultStatus");
				String resultMsg = bodyres.getJSONObject("resultInfo").getString("resultMsg");
				if(resultstatus.equals("S")) {

					resultdata.put("success", true);
					resultdata.put("orderId", generatedOrderID);
					resultdata.put("txnToken", txnToken);
					resultdata.put("amount", amount);
					resultdata.put("message", resultMsg);
				}else {

					resultdata.put("success", false);
					resultdata.put("orderId", generatedOrderID);
					resultdata.put("amount", amount);
					resultdata.put("message", resultMsg);

				}



			}

			// System.out.append("Request: " + post_data);
			responseReader.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return resultdata;     
	}

	public static Map<String, Object> TransactionStatus() throws Exception {
		/* initialize an object */
		JSONObject paytmParams = new JSONObject();

		/* body parameters */
		JSONObject body = new JSONObject();

		/* Find your MID in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
		body.put("mid", PAYTM_MID);

		/* Enter your order id which needs to be check status for */
		body.put("orderId", "YOUR_ORDERID_Here");
				String checksum = PaytmChecksum.generateSignature(body.toString(), PAYTM_MERCHANT_KEY);
				/* head parameters */
				JSONObject head = new JSONObject();

				/* put generated checksum value here */
				head.put("signature", checksum);

				/* prepare JSON string for request */
				paytmParams.put("body", body);
				paytmParams.put("head", head);
				String post_data = paytmParams.toString();
				URL url = new URL(PAYTM_ENVIRONMENT+"/v3/order/status");
				Map<String, Object> resultdata =  new HashMap<>();
				try {
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setRequestProperty("Content-Type", "application/json");
					connection.setDoOutput(true);

					DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
					requestWriter.writeBytes(post_data);
					requestWriter.close();
					String responseData = "";
					InputStream is = connection.getInputStream();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));

					if ((responseData = responseReader.readLine()) != null) {
						System.out.append("Response: " + responseData);
						JSONObject obj = new JSONObject(responseData);
						JSONObject bodyres = obj.getJSONObject("body");
						String resultstatus = bodyres.getJSONObject("resultInfo").getString("resultStatus");
						String resultMsg = bodyres.getJSONObject("resultInfo").getString("resultMsg");
						if(resultstatus.equals("TXN_SUCCESS")) {
							resultdata.put("resultStatus", resultstatus);
							resultdata.put("message", resultMsg);
							resultdata.put("txnId", bodyres.getString("txnId"));
							resultdata.put("bankTxnId", bodyres.getString("bankTxnId"));
							resultdata.put("orderId", bodyres.getString("orderId"));
							resultdata.put("txnAmount", bodyres.getString("txnAmount"));
							resultdata.put("txnType", bodyres.getString("txnType"));
							resultdata.put("gatewayName", bodyres.getString("gatewayName"));
							resultdata.put("bankName", bodyres.getString("bankName"));
							resultdata.put("mid", bodyres.getString("mid"));
							resultdata.put("paymentMode", bodyres.getString("paymentMode"));
							resultdata.put("refundAmt", bodyres.getString("refundAmt"));
							resultdata.put("chargeAmount", bodyres.getString("chargeAmount"));

						}else {

							resultdata.put("resultStatus", resultstatus);
							resultdata.put("message", resultMsg);

						}
					}
					// System.out.append("Request: " + post_data);
					responseReader.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return resultdata;

	}
}


