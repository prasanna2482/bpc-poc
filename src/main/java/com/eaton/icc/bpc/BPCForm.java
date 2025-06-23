package com.eaton.icc.bpc;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class BPCForm extends HttpServlet
{

    private static final long serialVersionUID = 8159297062111360598L;
	public BPCForm()
    {
        SMTP_SERVER_NAME = "mail.ch.etn.com";
    }

 /*   public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }*/
   public void init(ServletConfig config) throws ServletException {
    super.init(config);
	   // Dummy secrets for testing GitHub secret scanning
          String githubToken = "ghp_RANDOMCHARACTERS1234567890123456789012345678";
          String awsSecretKey = "AKIAABCDEFGHIJKLMNOPQ";
          String slackWebhook = "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX";
    try {
        // Load init.sql from classpath (src/main/resources/sql/init.sql)
        InputStream in = getClass().getClassLoader().getResourceAsStream("sql/init.sql");
        if (in == null) {
            log("init.sql not found in classpath under sql/init.sql");
            return;
        }

        Scanner s = new Scanner(in).useDelimiter(";");
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Replace below with your actual DB details
        String url = "jdbc:mysql://poctest1.mysql.database.azure.com:3306/mysql?useSSL=true";
        String username = "dbadmin";
        String password = "mysql@123";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url, username, password);
             java.sql.Statement stmt = conn.createStatement()) {

            while (s.hasNext()) {
                String sql = s.next().trim();
                if (!sql.isEmpty()) {
                    stmt.execute(sql);
                }
            }

            log("Database initialized successfully from init.sql");
        }
    } catch (Exception e) {
        log("Error during database initialization: ", e);
        throw new ServletException("Failed to initialize DB", e);
    }
}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuffer tmpStringBuffer = new StringBuffer(2048);
        String initiatedBy = "";
        String parameters[] = new String[31];
        parameters[0] = request.getParameter("name");
        parameters[1] = request.getParameter("from");
        parameters[2] = request.getParameter("phone");
        parameters[3] = request.getParameter("to");
        parameters[4] = request.getParameter("gono");
        parameters[5] = request.getParameter("person");
        parameters[6] = request.getParameter("region");
        parameters[7] = request.getParameter("district");
        parameters[8] = request.getParameter("plant").equals("") ? "" : request.getParameter("plant");
        parameters[9] = request.getParameter("product");
        if(request.getParameter("distributor") != null)
            parameters[10] = request.getParameter("distributor").equals("") ? "" : request.getParameter("distributor");
        else
            parameters[10] = "null";
        if(request.getParameter("vista1") != null)
            parameters[11] = request.getParameter("vista1").equals("") ? "" : request.getParameter("vista1");
        else
            parameters[11] = "null";
        if(request.getParameter("location1") != null)
            parameters[12] = request.getParameter("location1").equals("") ? "" : request.getParameter("location1");
        else
            parameters[12] = "null";
        if(request.getParameter("customer") != null)
            parameters[13] = request.getParameter("customer").equals("") ? "" : request.getParameter("customer");
        else
            parameters[13] = "null";
        if(request.getParameter("vista2") != null)
            parameters[14] = request.getParameter("vista2").equals("") ? "" : request.getParameter("vista2");
        else
            parameters[14] = "null";
        if(request.getParameter("location2") != null)
            parameters[15] = request.getParameter("location2").equals("") ? "" : request.getParameter("location2");
        else
            parameters[15] = "null";
        parameters[16] = request.getParameter("project").equals("") ? "" : request.getParameter("project");
        parameters[17] = request.getParameter("vista3").equals("") ? "" : request.getParameter("vista3");
        parameters[18] = request.getParameter("location3").equals("") ? "" : request.getParameter("location3");
        parameters[19] = request.getParameter("suspItem") != null ? request.getParameter("suspItem") : "No";
        parameters[20] = request.getParameter("zAccount") != null ? request.getParameter("zAccount") : "No";
        parameters[21] = request.getParameter("custNumber").equals("") ? "" : request.getParameter("custNumber");
        parameters[22] = request.getParameter("invoiceNumber").equals("") ? "" : request.getParameter("invoiceNumber");
        parameters[23] = request.getParameter("suspenseNumber").equals("") ? "" : request.getParameter("suspenseNumber");
        parameters[24] = request.getParameter("amount").equals("") ? "" : request.getParameter("amount");
        parameters[25] = request.getParameter("settlement");
        parameters[26] = request.getParameter("bpc_manager") != null ? request.getParameter("bpc_manager") : "";
        if(request.getParameter("sales_office") != null)
            parameters[27] = request.getParameter("sales_office").equals("") ? "" : request.getParameter("sales_office");
        else
            parameters[27] = "null";
        if(parameters[26] != "")
            initiatedBy = parameters[26] + " " + parameters[27];
        else
            initiatedBy = parameters[27];
        parameters[28] = request.getParameter("reason");
        parameters[29] = request.getParameter("actions");
        parameters[30] = request.getParameter("item_number");
        tmpStringBuffer.append("------------- Business Policy Concession Form ------------\n");
        tmpStringBuffer.append("Date of Submission: " + getCurrentSqlDateString() + "\n\n");
        tmpStringBuffer.append("Name: " + parameters[0] + "\n");
        tmpStringBuffer.append("E-mail Address: " + parameters[1] + "\n");
        tmpStringBuffer.append("Phone: " + parameters[2] + "\n");
        tmpStringBuffer.append("Subject: Advice of Customer Claim Settlement Request. \n");
        tmpStringBuffer.append("To: " + parameters[3] + "\n\n");
        tmpStringBuffer.append("G.O.No: " + parameters[4] + "\t\tSales Person ID: " + parameters[5] + "\n");
        tmpStringBuffer.append("Item No: " + parameters[30]);
        tmpStringBuffer.append("Region: " + parameters[6] + "\t\tDistrict: " + parameters[7] + "\n");
        tmpStringBuffer.append("Plant: " + parameters[8] + "\t\tProduct Involved: " + parameters[9] + "\n\n");
        tmpStringBuffer.append("Distributor: " + noNull(parameters[10]) + "\t\tVISTA: " + noNull(parameters[11]) + "\t\tLOCATION: " + noNull(parameters[12]) + "\n");
        tmpStringBuffer.append("Contractor/CUSTOMER: " + noNull(parameters[13]) + "\t\tVISTA: " + noNull(parameters[14]) + "\t\tLOCATION: " + noNull(parameters[15]) + "\n");
        tmpStringBuffer.append("Project: " + noNull(parameters[16]) + "\t\tEndUser Name: " + parameters[17] + "\t\tLOCATION: " + parameters[18] + "\n\n");
        tmpStringBuffer.append("Is This Request a Suspense Item: " + parameters[19] + "\n");
        tmpStringBuffer.append("Is This Item Currently on the Z-Account: " + parameters[20] + "\n");
        tmpStringBuffer.append("Customer Number: " + parameters[21] + "\n");
        tmpStringBuffer.append("Invoice Number: " + parameters[22] + "\n");
        tmpStringBuffer.append("Suspense Number: " + parameters[23] + "\n\n");
        tmpStringBuffer.append("TOTAL AMOUNT OF CLAIM: " + parameters[24] + "\n");
        tmpStringBuffer.append("SETTLEMENT: " + parameters[25] + "\n\n");
        tmpStringBuffer.append("SETTLEMENT ACTION INITIATED BY: " + initiatedBy + "\n");
        tmpStringBuffer.append("BRIEF REASON FOR CONCESSION: " + parameters[28] + "\n\n");
        tmpStringBuffer.append("PREVIOUS ACTIONS TAKEN TO RESOLVE THE ISSUE: " + parameters[29] + "\n\n");
        tmpStringBuffer.append("BPC ID No:___________________ Root Cause Code:_________________________\n");
        tmpStringBuffer.append("[  ] Parking BY:\n");
        tmpStringBuffer.append("[  ] Allow Deduction     $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("[  ] Re-Bill Customer    $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("[  ] Issue Credit        $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("[  ] BPC GO# \t\t $Amount_____________ Charge to ________________\n");
        tmpStringBuffer.append("[  ] Journal Entry       $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("                         $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("                         $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("                         $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("[  ] Check               $Amount_____________ Charge to________________\n");
        tmpStringBuffer.append("\t                 Total $_____________\n");
        tmpStringBuffer.append("See Below the required fields:\n");
        tmpStringBuffer.append("Customer Tax ID No._________________________________________\n");
        tmpStringBuffer.append("Customer Name:____________________________________________\n");
        tmpStringBuffer.append("Address:__________________________________________________\n");
        tmpStringBuffer.append("Address:__________________________________________________\n");
        tmpStringBuffer.append("City: & State:________________________________Zip:__________\n");
        tmpStringBuffer.append("Attn:_________________________________________\n");
        tmpStringBuffer.append("By:______________________________  Date:____________________\n");
        message = tmpStringBuffer.toString();
        msgSubject = "Subject:" + parameters[4] + " " + noNull(parameters[10]) + " " + noNull(parameters[13]) + " " + noNull(parameters[16]);
        msgFrom = parameters[3];
        msgTo = parameters[1];
        msgCC = parameters[3];
        try
        {
            sendMail(msgFrom, msgTo, msgCC, message, msgSubject);
            out.println("<html><head><title>Business Plicy Concession</title><meta http-equiv=refresh content=0;URL=bpc_confirm.html></head><body></body></html>");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new ServletException("an Error has occurred while sending email  " + e.getMessage());
        }
    }

    private String getCurrentSqlDateString()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        return formatter.format(currentDate);
    }

    public boolean sendMail(String sender, String recipient, String copy, String message, String subject)
    {
        try
        {
        	Properties properties = System.getProperties();
        	properties.setProperty("mail.smtp.host", SMTP_SERVER_NAME);
        	
        	Session session = Session.getDefaultInstance(properties);
        	MimeMessage mailMessage = new MimeMessage(session);
        	
        	mailMessage.setFrom(new InternetAddress(sender));
        	mailMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
        	mailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(copy));
        	mailMessage.setSubject(subject);
        	
        	mailMessage.setText(message);
        	
        	Transport.send(mailMessage);
        }
        catch(MessagingException e)
        {
            log("error occured while sending mail", e);
            return false;
        }
        return true;
    }

    public static String noNull(String s)
    {
        if(s == "null")
            return "";
        else
            return s;
    }

    String message;
    String msgFrom;
    String msgTo;
    String msgCC;
    String msgSubject;
    String SMTP_SERVER_NAME;
}//end of class   
