var bodyParser = require('body-parser');

var express = require('express');

var _ = require('underscore');

var db = require('./db');



//fetch a query

var executeQuery = async function (query) {

  var connectionPool = await db;

  var result = await connectionPool.request().query(query);

  return result.recordset;

}

//send json formatted record set as a response

var sendQueryResults = async function (res, query) {

  try {

    var recordset = await executeQuery(query);

    res.json(recordset);
    //res.send.JSON(recordset);
   // res.send('youre logged in');

  }

  catch (err) {

    res.send({

      success: false,

      error: err

    });

  }

};

var getQueryResults = async function (res, query) {

  try {

    var recordset = await executeQuery(query);

    //res.json(recordset);

    var obj = recordset[0];
    console.log(recordset.length);
    if(recordset.length == 1){
      res.json(obj);
      //res.send.JSON(obj);
    }
    else{
      // 403 - access denied code
      //res.send(403);
      res.sendStatus(403)
    }
  // if(recordset.length == 1){
  //     if(obj.UserType == 'owner'){
  //       res.redirect('/add_property_form');
  //     }
  //     else{
  //       res.redirect('/search_workspace_form');
  //     }
  //     console.log('youre logged in');
  //   }
  //   else{
  //     res.send('Access Denied! Check your credentials!');
  //   }
  }

  catch (err) {

    res.send({

      success: false,

      error: err

    });

  }

};

//create a routes to access the page from front end 

module.exports = function () {

  //var app = express.Router()

  var app = express();

  app.use(bodyParser.json());

  //CORS Middleware
  app.use(function (req, res, next) {
  //Enabling CORS 
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With,  Accept, Authorization");
  res.header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
  next();
});

 //---------------------------------------------------------------------//
 //Web page/form references
  app.get('/', function (req, res) {
    res.sendFile( __dirname + "/" + "login_page.html" );
  });

  app.get('/owner_registration_form', function (req, res) {
    res.sendFile( __dirname + "/" + "owner_registration_form.html" );
  });

  app.get('/renter_registration_form', function (req, res) {
    res.sendFile( __dirname + "/" + "renter_registration_form.html" );
  });

  app.get('/add_property_form', function (req, res) {
    res.sendFile( __dirname + "/" + "add_property_form.html" );
  });

  app.get('/add_workspace_form', function (req, res) {
    res.sendFile( __dirname + "/" + "add_workspace_form.html" );
  });

  app.get('/search_property_form', function (req, res) {
    res.sendFile( __dirname + "/" + "search_property_form.html" );
  });

  app.get('/search_workspace_form', function (req, res) {
    res.sendFile( __dirname + "/" + "search_workspace_form.html" );
  });

  
//---------------------------------------------------------------------//
//Login logic
  var urlencodedParser = bodyParser.urlencoded({ extended: false })
  app.post('/login', urlencodedParser, function (req, res) {  

    response = {
      login:req.body.login,
      password:req.body.password
    };    
    //var query = "SELECT  FROM Users WHERE [Login] LIKE '"+response.login+"' AND [Password] LIKE '"+response.password+"'";
    //var query = "SELECT COUNT(UserID) AS MemberCount FROM Users WHERE [Login] LIKE '" + response.login +"' AND [Password] LIKE '" + response.password +"'";
    var query = "EXEC spLogin @Login = '" + response.login +"', @Password = '" + response.password +"'";
    getQueryResults(res, query);


  });  


  //---------------------------------------------------------------------//
  //Displaying property and workspace listings
  app.get('/property-listing-page/', function (req, res) {
    var query = "SELECT * FROM [dbo].[Property]";
    sendQueryResults(res, query);
    console.log("data from database sent to client")
   });

   app.get('/property-listing-page/:id', function (req, res) {
    var query = "SELECT * FROM [dbo].[Property] WHERE UserID = " + req.params.id + "";
    sendQueryResults(res, query);
    console.log("data from database sent to client")
   });


   app.get('/workspace-listing-page/', function (req, res) {
    //var query = "SELECT * FROM [dbo].[Workspace]";
    var query = 'SELECT ws.WorkspaceID, ws.PropertyID, wst.WorkspaceType, lp.DayPrice, lp.WeekPrice, lp.MonthPrice, ws.CapacitySeats, ws.Smoking \n' +
                'FROM Workspace AS ws \n' +
                'JOIN WorkspaceType AS wst \n' +
                'ON ws.WorkspaceTypeID = wst.WorkspaceTypeID \n' +
                'JOIN LeasePrice AS lp \n' +
                'ON ws.LeasePriceID = lp.LeasePriceID';
    sendQueryResults(res, query);
    console.log("data from database sent to client")
   });

   app.get('/workspace-listing-page/:id', function (req, res) {
   var query = 'SELECT ws.WorkspaceID, ws.PropertyID, wst.WorkspaceType, lp.DayPrice, lp.WeekPrice, lp.MonthPrice, ws.CapacitySeats, ws.Smoking \n' +
                'FROM Workspace AS ws \n' +
                'JOIN WorkspaceType AS wst \n' +
                'ON ws.WorkspaceTypeID = wst.WorkspaceTypeID \n' +
                'JOIN LeasePrice AS lp \n' +
                'ON ws.LeasePriceID = lp.LeasePriceID  \n' +
                'JOIN Property AS p  \n' +
				        'ON p.PropertyID = ws.PropertyID  \n' +
				        "WHERE p.UserID = " + req.params.id + "";
    sendQueryResults(res, query);
    console.log("data from database sent to client")
   });


//---------------------------------------------------------------------//
//Users registration
    var urlencodedParser = bodyParser.urlencoded({ extended: false })

    app.post('/register-user', urlencodedParser, function (req, res) {
      response = {
        first_name:req.body.first_name,
        last_name:req.body.last_name,
        login:req.body.login,
        password:req.body.password,
        phone:req.body.phone,
        email:req.body.email,
        user_type_id: req.body.user_type_id
      };
      var mysql = "INSERT INTO  Users (UserTypeID, FirstName, LastName, Phone, Email, Login, Password) VALUES( " + response.user_type_id + ", '" + response.first_name +"','" + response.last_name +"',' "+ response.phone + "','" + response.email +"', '" + response.login +"', '" + response.password +"')";
      sendQueryResults(res, mysql);
      res.send("success")
    });

//---------------------------------------------------------------------//
//Add propery and workspace
    app.post('/add-property', urlencodedParser, function (req, res) {
      response = {
          user_id:req.body.user_id,
          //property_id:req.body.property_id,
          floor_space:req.body.floor_space,
          neighbourhood_name:req.body.neighbourhood_name,
          street_number:req.body.street_number,
          street_name:req.body.street_name,
          city:req.body.city,
          postal_code:req.body.postal_code,
          parking_availability:req.body.parking_availability,
          public_transportation:req.body.public_transportation
        };
        //console.log( "Add property" + response.user_id + response.neighbourhood_name)        
        var mysql = "INSERT INTO Property (UserID, FloorSpaceSQFT, NeighbourhoodName, StreetNumber, StreetName, City, PostalCode, ParkingAvailability, PublicTransportation) Values( " + response.user_id + "," + response.floor_space +", '" + response.neighbourhood_name +"', " + response.street_number +", '" + response.street_name +"', '" + response.city +"', '" + response.postal_code +"', '" + response.parking_availability +"', '" + response.public_transportation +"')";
        sendQueryResults(res, mysql);
        res.send("success")
    });


    app.post('/add-workspace', urlencodedParser, function (req, res) {
      response = {
          property_id:req.body.property_id,
          workspace_type_id:req.body.workspace_type_id,
          //lease_price_id:req.body.lease_price_id,
          capacity:req.body.capacity,
          smoking:req.body.smoking,
          per_day:req.body.per_day,
          per_week:req.body.per_week,
          per_month:req.body.per_month
          };
        console.log(response)        
        var mysql = "INSERT INTO LeasePrice (DayPrice, WeekPrice, MonthPrice) VALUES(" + response.per_day +"," + response.per_week +"," + response.per_month +"); INSERT INTO Workspace(PropertyID, WorkspaceTypeID, LeasePriceID, CapacitySeats, Smoking) VALUES(" + response.property_id +"," + response.workspace_type_id +", (SELECT MAX(LeasePriceID) FROM LeasePrice)," + response.capacity +",'" + response.smoking +"');";
        sendQueryResults(res, mysql);
        res.send("success")
    });


//------------Update property and workspace ---------------------------------------------------------//

    app.put('/update-property', urlencodedParser, function (req, res) {
      response = {
          property_id:req.body.property_id,
          floor_space:req.body.floor_space,
          neighbourhood_name:req.body.neighbourhood_name,
          street_number:req.body.street_number,
          street_name:req.body.street_name,
          city: req.body.city,
          postal_code:req.body.postal_code,
          parking_availability:req.body.parking_availability,
          public_transportation:req.body.public_transportation
        };

        // if(response.property_id === ''){
        //   response.property_id = 'NULL';
        // }
        if(response.floor_space === ''){
          response.floor_space = 'NULL';
        }
        if(response.neighbourhood_name === ''){
          response.neighbourhood_name = 'NULL';
        }
        if(response.street_number === ''){
          response.street_number = 'NULL';
        }
        if(response.street_name === ''){
          response.street_name = 'NULL';
        }
        if(response.city === ''){
          response.city = 'NULL';
        }
        if(response.postal_code === ''){
          response.postal_code = 'NULL';
        }
        if(response.parking_availability === ''){
          response.parking_availability = 'NULL';
        }
        if(response.public_transportation === ''){
          response.public_transportation = 'NULL';
        }
      
        var mysql = "EXEC spUpdatePropertyOptional\n" +
                    "@PropertyID =" + response.property_id + ",\n" +
                    "@FloorSpaceSQFT =" + response.floor_space + ",\n" +
                    "@NeighbourhoodName =" + response.neighbourhood_name + ",\n" +
                    "@StreetNumber =" + response.street_number + ",\n" +
                    "@StreetName =" + response.street_name + ",\n" +
                    "@City =" + response.city + ",\n" +
                    "@PostalCode =" + response.postal_code + ",\n" +
                    "@ParkingAvailability =" + response.parking_availability + ",\n" +
                    "@PublicTransportation =" + response.public_transportation + ";"
        //console.log(response);
        sendQueryResults(res, mysql);
        res.send("success")
      });
    

      app.put('/update-workspace', urlencodedParser, function (req, res) {
        response = {
            workspace_id: req.body.workspace_id,
            property_id: req.body.property_id,
            workspace_type_id: req.body.workspace_type_id,
            per_day: req.body.per_day,
            per_week: req.body.per_week,
            per_month: req.body.per_month,
            capacity: req.body.capacity,
            smoking: req.body.smoking,
          };
  
          // if(response.property_id === ''){
          //   response.property_id = 'NULL';
          // }
          if(response.property_id === ''){
            response.property_id = 'NULL';
          }
          if(response.workspace_type_id === ''){
            response.workspace_type_id = 'NULL';
          }
          if(response.per_day === ''){
            response.per_day = 'NULL';
          }
          if(response.per_week === ''){
            response.per_week = 'NULL';
          }
          if(response.per_month === ''){
            response.per_month = 'NULL';
          }
          if(response.capacity === ''){
            response.capacity = 'NULL';
          }
          if(response.smoking === ''){
            response.smoking = 'NULL';
          }
        
          var mysql = "EXEC spUpdateWorkspaceOptional\n" +
                      "@WorkspaceID =" + response.workspace_id + ",\n" +
                      "@PropertyID =" + response.property_id + ",\n" +
                      "@WorkspaceTypeID =" + response.workspace_type_id + ",\n" +
                      "@DayPrice =" + response.per_day + ",\n" +
                      "@WeekPrice =" + response.per_week + ",\n" +
                      "@MonthPrice =" + response.per_month + ",\n" +
                      "@CapacitySeats =" + response.capacity + ",\n" +
                      "@Smoking =" + response.smoking + ";"
          
          console.log(response);
          sendQueryResults(res, mysql);
          res.send("success")
        });


//---------- Delete property and workspace-----------------------------------------------------------//

    app.delete('/delete-property/:propertyID', urlencodedParser, function (req, res) {
      var mysql = "EXEC spDeletePropertyByID @PropertyID = " + req.params.propertyID + "";
      sendQueryResults(res, mysql);
      res.send("success")
    });


    app.delete('/delete-workspace/:workspaceID', urlencodedParser, function (req, res) {
        var mysql = "EXEC spDeleteWorkspaceByID @WorkspaceID = " + req.params.workspaceID + "";
        sendQueryResults(res, mysql);
        res.send("success")
      });
//---------------------------------------------------------------------//
// Search

app.post('/search-workspace-form', urlencodedParser, function (req, res) {
  response = {
    workspace_type: "'" + req.body.workspace_type + "'",
    capacity_from:req.body.capacity_from,
    capacity_to:req.body.capacity_to
  };
  if(response.workspace_type === "''"){
    response.workspace_type = 'NULL';
  }
  if(response.capacity_from === ''){
    response.capacity_from = 'NULL';
  }
  if(response.capacity_to === ''){
    response.capacity_to = 'NULL';
  }
 //var mysql = "EXEC spSearchWSTest @WorkspaceType = '" + response.workspace_type + "', @CapacityFrom = '" + response.capacity_from + "', @CapacityTo = " + response.capacity_to + "";
  var mysql = "EXEC spSearchWSTest\n" +
              "@WorkspaceType =" + response.workspace_type + ",\n" +
              "@CapacityFrom =" + response.capacity_from + ",\n" +
              "@CapacityTo =" + response.capacity_to + ";"; 
  
  sendQueryResults(res, mysql);
});

app.post('/search-property-form', urlencodedParser, function (req, res) {
  response = {
    street_name:req.body.street_name,
    city:req.body.city,
    neighbourhood:req.body.neighbourhood
  };
  if(response.street_name === ''){
    response.street_name = 'NULL';
  }
  if(response.city === ''){
    response.city = 'NULL';
  }
  if(response.neighbourhood === ''){
    response.neighbourhood = 'NULL';
  }

  var mysql = "EXEC spSearchProperty\n" +
              "@StreetName =" + response.street_name + ",\n" +
              "@City =" + response.city + ",\n" +
              "@NeighbourhoodName =" + response.neighbourhood + ";"; 
  
  sendQueryResults(res, mysql);
});

  return app;

 

}