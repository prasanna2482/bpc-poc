
# Business Policy Concession

A BPC budget is available so that Eaton Electrical can be responsive to customer needs and disputes over controversial issues. If the issue is not a field sales error or a product performance related issue, field sales can initiate a BPC claim. BPC provides managers with an excellent tool to "do the right thing" commercially on behalf of Eaton. The Zone BPC account is intended to be used as a vehicle to make a commercial concession to a valued customer that is above and beyond Eaton’s normal contractual responsibility. Field sales errors should be covered by using the local field sales concession account (Z-account).
Product Lines should normally be the source of total or partial funds for a concession when product line quality is the root cause of the problem on which the claim is based.BPC is NOT intended to be used to cover contractual warranty expenses that are clearly the responsibility of a product line.

## Prerequisites

    1. Java 17
    2. Text Editor or IDE
    3. Tomcat 10
    4. Apache Maven 3.6+
    5. Git
## Building Project

    1. Install Git
    2. Setup Maven (If not available in IDE)
    2. Git Clone TAP Repository
    3. Import cloned repository in IDE
        i. File -> Import Existing Maven Projects -> Select project directory -> Finish
        ii. Right Click on project -> Run as -> Maven Clean -> Maven Install
        iii. This will generate a WAR file in target folder.

## Deployment

To deploy this project we need Tomcat 10 Server

    1. Add Tomcat server in IDE
        i. New -> Apache -> Tomcat v10.0 -> Installation directory -> Finish

    5. Start Tomcat Server from IDE
        i. Servers -> Right click on Tomcat server -> Start
        
    6. Deploy WAR file (2 Options)
        i. Deploy WAR file from IDE 
            Right click on running Tomcat server -> Add and remove -> select war file to deploy

        ii. Deploy WAR file from Tomcat console (Preferred)
            Tomcat console -> Manager App -> select war file to deploy
## Application URL

    1. Application URLs can change depending on the server and deployment, although a few examples are shown below.
        i. Standard URL format
            protocol://host:port/ApplicationContext

        ii. OpenShift URL
            Dev - 
            QA -
            Prod -

        iii. Run Locally 
            http://localhost:8080/bpc/bpc.html