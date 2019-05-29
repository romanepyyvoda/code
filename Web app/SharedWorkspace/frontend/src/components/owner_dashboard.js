import React, { Component } from 'react';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import Property from './property';
import Workspace from './workspace';
import '../css/style.css';

class OwnerDash extends Component {
  constructor(props){
    super(props)
    this.state={
			ownerid: this.props.ownerInfoProp,
    }
  }



    render() {
    return (
    <Router>
        <div className="dashboard">
          <nav>
          <ul>
            <li><Link to={'/myproperties'}> My Properties </Link></li>
            <li><Link to={'/myworkspaces'}> My Workspaces</Link></li>
          </ul>
          </nav>
          <hr />
          <Switch>
              <Route exact path="/myproperties" render={() => <Property  owneridprops={this.state.ownerid} /> } />
              <Route path="/myworkspaces" render={() => <Workspace  owneridprops={this.state.ownerid} /> } />
          </Switch>
        </div>
      </Router>
    );
  }
}

export default OwnerDash;