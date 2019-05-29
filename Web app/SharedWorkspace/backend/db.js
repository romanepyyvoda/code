var sql = require('mssql');

//config for your database

var config = {

    user: 'workspaceAdmin',

    password: 'adminadmin',

    server: 'localhost', 

    database: 'SharedWorkspace'  

};

//connect to your database, node module export format

module.exports = new sql.ConnectionPool(config)

    .connect()

    .then(pool => {

        console.log('Connected to MSSQL DB SharedWorkspace.com');

        return pool;

    })

    .catch(err => console.log('Database Connection Error: ', err));