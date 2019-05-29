import React, { Component } from 'react';
import '../css/style.css';
import axios from 'axios';

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
          username: '',
          password: '',
          error: '',
          registration_message: this.props.registrationMSG,
        };
    this.handlePassChange = this.handlePassChange.bind(this);
    this.handleUserChange = this.handleUserChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.dismissError = this.dismissError.bind(this);
    this.dismissMSG = this.dismissMSG.bind(this);
    //this.processResponse = this.processResponse.bind(this);
    }


    dismissError() {
        this.setState({ error: '' });
    }
    dismissMSG() {
      this.setState({ registration_message: '' });
  }


    handleSubmit(evt) {
        evt.preventDefault();
    
        if (!this.state.username) {
          return this.setState({ error: 'Username is required' });
        }
    
        if (!this.state.password) {
          return this.setState({ error: 'Password is required' });
        }

        axios.post('http://localhost:5002/login', {
          login: this.state.username,
          password: this.state.password,
        })
        .then(res => 
          {
            //console.log(res.data);
            if(res.data === 403){
              return this.setState({ error: 'Access Denied! Login or password is incorrect!' });
            }
            else{
              if(res.data.UserType === 'owner'){
                this.props.goOwnerDashProp(res.data);
              }
              if(res.data.UserType === 'renter'){
                this.props.goRenterDashProp(res.data);
              }
            }
          })
          .catch(error => {
            //console.log('this '+error);
            return this.setState({ error: 'Access Denied! Login or password is incorrect!' });
          });
       
        return this.setState({ error: '' });
    }

    


    handleUserChange(evt) {
        this.setState({
          username: evt.target.value,
        });
      };
    

    handlePassChange(evt) {
        this.setState({
          password: evt.target.value,
        });
    }

    
    render() {
        return (
            <div className="main-container">
                {
                  this.state.registration_message &&
                  <div className="msg-box" data-test="error" onClick={this.dismissMSG}>
                    {this.state.registration_message}
                  </div>
                }
                <h2>Login</h2>
              <form onSubmit={this.handleSubmit}>
                {
                  this.state.error &&
                  <div className="error-box" data-test="error" onClick={this.dismissError}>
                    <button className="buttonClose" onClick={this.dismissError}>✖</button>
                    {this.state.error}
                  </div>
                }
                <div className="field-container">
                    <label>User Name</label><br></br>
                    <input type="text" data-test="username" value={this.state.username} onChange={this.handleUserChange} />
                </div>
                                
                <div className="field-container">
                    <label>Password</label><br></br>
                    <input type="password" data-test="password" value={this.state.password} onChange={this.handlePassChange} />
                </div>
                
                <div className="field-container">
                    <button className="buttonLogin" onClick={this.handleSubmit}>Login</button>
                </div>
            </form>
                <div>
                    <span>Do not have an account?&nbsp;</span><button className="buttonReg" onClick={this.props.goRegister}>Register</button>
                </div>
              
            </div>
      );
    }
}

export default Login;