import React, { Component } from 'react';
//import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import Login from './components/login_page';
import Register from './components/register';
import './css/style.css';
//import PropertyCRUD from './components/propertyCRUD';
//import WorkspaceCRUD from './components/workspaceCRUD';
import OwnerDash from './components/owner_dashboard';
//import Workspace from './components/workspace';
//import Property from './components/property';
import RenterDash from './components/renter_dashboard';
//import axios from 'axios';



export class App extends Component {
  constructor(props){
		super(props)
		this.state={
			//items: [],
      isLoggedIn: true,
      toRegister: false,
      toOnwerDash: false,
      toRenterDash: false,
      registerMessage: "",
    }
    this.renderRegister = this.renderRegister.bind(this);
    this.renderLogin = this.renderLogin.bind(this);
    this.renderOwnerDash = this.renderOwnerDash.bind(this);
    this.renderRenterDash = this.renderRenterDash.bind(this);
    this.HandleLogout = this.HandleLogout.bind(this);
    this.HandleSuccessfulRegistration = this.HandleSuccessfulRegistration.bind(this);
  }

  renderLogin = () =>{
    if (!this.state.isLoggedIn) {
      this.setState({
        toRegister: false,
        isLoggedIn: true,
        toOnwerDash: false,
        toRenterDash: false,
      });
    }
    return(
      <Login goRegister = {this.renderRegister} goOwnerDashProp = {this.renderOwnerDash}
       goRenterDashProp = {this.renderRenterDash} registrationMSG = {this.state.registerMessage} />
    );
  }
  
  renderRegister = () =>{
    if (!this.state.toRegister) {
      this.setState({
        toRegister: true,
        isLoggedIn: false,
        toOnwerDash: false,
        toRenterDash: false,
      });
    }
    return(
      <Register goLogin = {this.HandleSuccessfulRegistration}/>
    );
  }

  renderOwnerDash = (ownerparams) =>{
    if (!this.state.toOnwerDash) {
      this.setState({
        toRegister: false,
        isLoggedIn: false,
        toOnwerDash: true,
        toRenterDash: false,
        userparams: ownerparams,
      });
    }
    return(
      <OwnerDash ownerInfoProp = {ownerparams.UserID}/>
    );
  }

  renderRenterDash = (renterparams) =>{
    if (!this.state.toRenterDash) {
      this.setState({
        toRegister: false,
        isLoggedIn: false,
        toOnwerDash: false,
        toRenterDash: true,
        userparams: renterparams,
      });
    }
    return(
      <RenterDash renterInfoProp = {renterparams}/>
    );
  }

  HandleLogout(){
    this.setState({
      registerMessage: ""
    });
    this.renderLogin();
  }

  HandleSuccessfulRegistration(){
    this.setState({
      registerMessage: "Registration is successful! Please, log in."
    });
    this.renderLogin();
  }

    
  render() {
    var {isLoggedIn, toRegister, toOnwerDash, toRenterDash} = this.state;
    if(isLoggedIn)
    return (
      <div className="maindiv">
        <div className="banner">Shared Workspace</div>
        {this.renderLogin()}
      </div>
    );
    else if(toOnwerDash){
      return(
        <div className="maindiv">
          <div className="banner">Shared Workspace <span className="greeting">Hello, {this.state.userparams.Name}</span>
          <span onClick={this.HandleLogout}><img height="40px" alt="logout" src={ require('./img/logout_icon.png') } /></span></div>
          {this.renderOwnerDash(this.state.userparams)}
        </div>
      );
    }
    else if(toRenterDash)
    return(
      <div className="maindiv">
        <div className="banner">Shared Workspace <span className="greeting">Hello, {this.state.userparams.Name}</span>
          <span onClick={this.HandleLogout}><img height="40px" alt="logout" src={ require('./img/logout_icon.png') } /></span></div>
        {this.renderRenterDash(this.state.userparams)}
      </div>
    );
    else if(toRegister)
    return(
      <div className="maindiv">
        <div className="banner">Shared Workspace</div>
        {this.renderRegister()}
      </div>
    );
  }
}

export default App;
