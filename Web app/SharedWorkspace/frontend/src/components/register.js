import React, { Component } from 'react';
import axios from 'axios';
import '../css/style.css';

class Register extends Component {

  constructor() {
    super();
    this.state = {
      firstname: '',
      lastname: '',
      role: '',
      login: '',
      password: '',
      email: '',
      phone: '',
      error: ''
    };
    this.handlePassChange = this.handlePassChange.bind(this);
    this.handleLoginChange = this.handleLoginChange.bind(this);
    this.handleFirstNameChange = this.handleFirstNameChange.bind(this);
    this.handleLastNameChange = this.handleLastNameChange.bind(this);
    this.handleRoleChange = this.handleRoleChange.bind(this);
    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.handlePhoneChange = this.handlePhoneChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.dismissError = this.dismissError.bind(this);
    this.Register = this.Register.bind(this);
  }

  dismissError() {
    this.setState({ error: '' });
  }

  handleSubmit(evt) {
    evt.preventDefault();

    if (!this.state.firstname) {
      return this.setState({ error: 'First Name is required' });
    }

    if (!this.state.lastname) {
      return this.setState({ error: 'Last Name is required' });
    }

    if (!this.state.role ) {
      return this.setState({ error: 'Role is required' });
    }

    if (!this.state.login) {
      return this.setState({ error: 'Login is required' });
    }

    if (!this.state.password) {
      return this.setState({ error: 'Password is required' });
    }
    
    if (!this.state.email) {
      return this.setState({ error: 'Email is required' });
    }

    if (!this.state.phone) {
      return this.setState({ error: 'Phone is required' });
    }

    this.setState({ error: '' });
    this.Register();
  }


  handleLoginChange(evt) {
      this.setState({
        login: evt.target.value,
      });
    };


  handlePassChange(evt) {
      this.setState({
        password: evt.target.value,
      });
  }

  handleEmailChange(evt) {
    this.setState({
      email: evt.target.value,
    });
  };


  handlePhoneChange(evt) {
      this.setState({
        phone: evt.target.value,
      });
  }

  handleFirstNameChange(evt) {
    this.setState({
      firstname: evt.target.value,
    });
  };


  handleLastNameChange(evt) {
    this.setState({
      lastname: evt.target.value,
    });
  }

  handleRoleChange(evt) {
    this.setState({
      role: evt.target.value,
    });
  };

  Register(){
    var usertypeid;
    if(this.state.role === "Owner")
    { usertypeid = 2;}
    if(this.state.role === "Renter")
    { usertypeid = 1;}
    try{ 
      axios.post('http://localhost:5002/register-user', {
        first_name: this.state.firstname,
        last_name: this.state.lastname,
        login: this.state.login,
        password: this.state.password,
        phone: this.state.phone,
        email: this.state.email,
        user_type_id: usertypeid})
      .then(res => {
        console.log("register status: " + res.data);
        if(res.data === 'success')
        {
            this.props.goLogin();
        }
      })
      .catch(error => {
        console.log(error);
        return this.setState({ error: 'Something went wrong, ask TG for help!' });
      });
  }catch(e){
    console.log('request failed :' + {e});
  }
  }

  render() {
    return (
        <div className="main-container">
          <h2>Registration</h2>
          <form onSubmit={this.handleSubmit}>
                {
                  this.state.error &&
                  <div className="error-box" data-test="error" onClick={this.dismissError}>
                    <button className="buttonClose" onClick={this.dismissError}>✖</button>
                    {this.state.error}
                  </div>
                }
                <div className="field-container">
                    <label>First Name</label><br></br>
                    <input type="text" data-test="firstname" value={this.state.firstname} onChange={this.handleFirstNameChange} />
                </div>
                                
                <div className="field-container">
                    <label>Last Name</label><br></br>
                    <input type="text" data-test="lastname" value={this.state.lastname} onChange={this.handleLastNameChange} />
                </div>

                <div className="field-container">
                    <label>Select Your Role</label><br></br>
                    <input list="role" data-test="role" value={this.state.role} onChange={this.handleRoleChange} />
                    <datalist id="role">
                      <option value="Owner">Owner</option>/>
                      <option value="Renter">Renter</option>/>
                    </datalist>
                </div>

                <div className="field-container">
                    <label>Login</label><br></br>
                    <input type="text" data-test="login" value={this.state.login} onChange={this.handleLoginChange} />
                </div>
                                
                <div className="field-container">
                    <label>Password</label><br></br>
                    <input type="password" data-test="password" value={this.state.password} onChange={this.handlePassChange} />
                </div>

                <div className="field-container">
                    <label>Email</label><br></br>
                    <input type="text" data-test="email" value={this.state.email} onChange={this.handleEmailChange} />
                </div>
                                
                <div className="field-container">
                    <label>Phone</label><br></br>
                    <input type="text" data-test="phone" value={this.state.phone} onChange={this.handlePhoneChange} />
                </div>
                
                <div className="field-container">
                    <button className="buttonRegister" onClick="">Register</button>
                </div>
            </form>
        </div>
    );
  }
}

export default Register;