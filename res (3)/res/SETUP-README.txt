ASSIGNMENT 6 - Stocks-III
-----------------------
Running the JAR file:
1. Make sure the input.txt, portfolio.txt, input.csv are there in the src/ folder right on the level where .jar file is.
2. Run the jar file appropriately uisng command line.
3. Create a folder called src and put appropriate file folder 'stocksDataDump' there.
4. Keep the format.xslt, input.csv, input.txt, parser.xml, Strategy.xml also under src.
5. Open the terminal and go to appropriate file location. Now run the jar file there. 
6. To run the application with GUI view: java -jar stocks.jar -v GUI
7. TO run the application with Command line view: java -jar stocks.jar -v cli


We are using javafx library for generating graphs. So please add to the dependency javaFx.
JavaFX is available under the same license and business model as Java SE. 
This includes the ability for third party developers to distribute the runtime librairies with their application(s), subject to the terms and conditions of the license.
At this time, the JavaFX UI Controls source code has been contributed to the OpenJFX open source project; other JavaFX components are expected to follow in multiple phases. 
The code is available under the GPL v2 with Classpath Exception license, similar to other projects in OpenJDK. The Oracle JavaFX runtime and SDK implementations will continue to be released under the Oracle Binary Code License for the Java SE Platform.
https://www.oracle.com/downloads/licenses/binary-code-license.html
------------------ 

* For all GUI based operations, you will be able to see status if a typed entry is accepted in a text-entry field.
* All buttons for any operation become functional only when all entries are accepted.

* For performing any operations using files using GUI. Only use .txt files with format specifies below. 



Instructions to create a portfolio and add three diffferent stocks to it.

1. enter 'create_flexible_portfolio' when greeted with welcome message.
2. You will be asked to enter a new portfolio name. Enter the name of the portfolio you wish to create. eg: enter '111'.
3. you now will be asked to enter the symbol of stock you wish to buy. eg: IBM 
4. enter the date you wish to buy the stock on. eg: enter 2022-11-07
5. enter the quantity of the stock you want to purchase. eg: enter 2
6. enter the commission fee for the transaction. eg: enter 2, specifying 2USD. The commission fee is absolute amount.

Add stock # 2:
1. enter add_flexible_stock.
2. You will be asked to enter an old flexible portfolio name. Enter the name of the portfolio you wish to add stocks to. eg: enter '111'.
3. you now will be asked to enter the symbol of stock you wish to buy. eg: GOOG 
4. enter the date you wish to buy the stock on. eg: enter 2022-11-08
5. enter the quantity of the stock you want to purchase. eg: enter 6
6. enter the commission fee for the transaction. eg: enter 3, specifying 2USD. The commission fee is absolute amount.

Add stock # 3:
1. enter add_flexible_stock.
2. You will be asked to enter an old flexible portfolio name. Enter the name of the portfolio you wish to add stocks to. eg: enter '111'.
3. you now will be asked to enter the symbol of stock you wish to buy. eg: AAC 
4. enter the date you wish to buy the stock on. eg: enter 2022-11-09
5. enter the quantity of the stock you want to purchase. eg: enter 6
6. enter the commission fee for the transaction. eg: enter 3, specifying 2USD. The commission fee is absolute amount.

Fetching the cost basis:
1. enter cost_flexible_basis to get the cost basis of some flexible portfolio. enter 111
2. input the date on which you wish to get the cost basis. eg: enter 2022-11-09. The cost basis will be printed.

Fetching the value of the portfolio on a certain date:
1. enter portfolio_value to get the cost basis of some flexible portfolio. enter 111.
2. input the date on which you wish to get the value. eg: enter 2022-11-09. The value will be printed.

----------------------------------------
Format for FILE driven commands:

For any command using file. We are using a single file 'input.txt' for all kinds of actions. We only need to
modify the file inputs appropriately, save it, and then run the command you wish to execute using file.

Same constraint as command-line based input holds valid while operating portfolio by file.

1. create_flexible_file - to create a flexible portfolio using a file. Below is the text file format.

new_portfolio_name
stock_symbol
date
quantity
commission_fee

2. add_flexible_stock_file - to add stocks to an existing flexible portfolio.

old_portfolio_name
stock_symbol
date
quantity
commission_fee

3. sell_flexible_stock_file - to sell stocks from an existing flexible portfolio

old_portfolio_name
stock_symbol
date
quantity
commission_fee

5. create_inflexible_file - to create an inflexible portfolio using a file

line1: new_portfolio_name

each following line will be representation of a stock to be added to the portfolio. format:
stock_name quantity

eg:

9999
IBM 2
GOOG 4



