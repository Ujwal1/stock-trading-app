ASSIGNMENT 6 - Stocks-III
Our application comprises of three layers:
The list lists changes and justifications in the design along with new additions.
-------------------------------------------------------------------------------------
1. Model, View and Controller.
2. The model comprises of classes and interfaces representing the entities in our scope. 
3. View:
	In the view, we had a single Interface 'StockViewImpl' and its implementation 'StockView'.
	Additions:
	* We have introduced a new interface GUIView and its implmentation GUIViewImpl to support a new GUI view for our application.
	* In order to segragate different screens for different operations, we have introduced command design pattern for GUI.
		where all screens/frames are stored separately in 'guiscreens' folder under view. Correct view is loaded based on
		corresponding button click from GUIViewImpl.
4. Controller:
	Additions and changes:
	- The controller now has a new implementation of 'PortfolioManager' as 'GUIPortfolioManagerImpl' meant to interact with the
	model and the new GUI view. 
	- FeaturesImpl implements Features and extends GUIPortfolioManagerImpl. It provides all the GUI related features to the GUI view.
	- There are additions in the CreateFlexiblePortfolio command to incorporate allowing to apply dollar cost strategy to the new portfolio.

5. Model: 
	Changes and additions:
	- Up-until the previous assignment, we had separate un-related interfaces for Flexible and inflexible portfolios
	although the operations were the same. So, now we have modified the design to having single interface for common operations
	and PortfolioFlexible portfolio having flexible-portfolio specific operations.  
	- The IModelMain now has additional functionalities that need to be exposed to the client(the controller)
	like, creating a dollar cost stratergy and applying dollar-cost strategy to a portfolio. 
	- The new functionalities are added to existing ModelMain and not any extension of it because new features are used by 
	both old and new controller.
	- There a new interface IStrategy representing operations for a strategy and additional functionalities for 
	FLexiblePortfolio to apply a strategy to that portfolio.  