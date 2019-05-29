import React, { Component } from 'react';
import axios from 'axios';
import '../css/style.css';

class RenterDash extends Component {

    constructor() {
        super();
        this.state = {
            workspaces:[],
            WorkspaceType: '',
            CapacitySeatsFrom: '',
            CapacitySeatsTo: '',                 
        };
    
    this.handleWorkspaceType = this.handleWorkspaceType.bind(this);
    this.handleCapacitySeatsFrom = this.handleCapacitySeatsFrom.bind(this);
    this.handleCapacitySeatsTo = this.handleCapacitySeatsTo.bind(this);
    this.renderWorkspace = this.renderWorkspace.bind(this);
    this.Search = this.Search.bind(this);
    }

    handleWorkspaceType(evt){
        this.setState({
            WorkspaceType: evt.target.value,
          });
    };

    handleCapacitySeatsFrom(evt) {
        this.setState({
            CapacitySeatsFrom: evt.target.value,
          });
    };

    handleCapacitySeatsTo(evt) {
        this.setState({
            CapacitySeatsTo: evt.target.value,
          });
    };

renderWorkspace = ({WorkspaceID, PropertyID, WorkspaceType, DayPrice, WeekPrice, MonthPrice, CapacitySeats, Smoking}) => <div key={WorkspaceID} > {WorkspaceID}: {PropertyID}: {WorkspaceType}, ${DayPrice}/day, ${WeekPrice}/week, ${MonthPrice}/month, {CapacitySeats} seats, Smoking - {Smoking}</div>

Search(){
  try{ 
    axios.post('http://localhost:5002/search-workspace-form', {
    workspace_type: this.state.WorkspaceType,
    capacity_from: this.state.CapacitySeatsFrom,
    capacity_to: this.state.CapacitySeatsTo,
  })
    .then(res => {
      const workspaces = res.data       
      this.setState({workspaces});
      
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
        const { workspaces } = this.state;
        return (
          <div className="wrapper">
                <div className="left">
                <h2>Search results:</h2>
                <div>{workspaces.map(this.renderWorkspace)}</div>
                </div>
                <div className="right">
                <div className="main-container">
            <h2>Search Workspaces for Rent</h2>
              
                
                <div className="field-container">
                    <label>Workspace Type</label><br></br>
                    <input list="choice1" type="text" data-test="WorkspaceType" value={this.state.WorkspaceType} onChange={this.handleWorkspaceType} />
                    <datalist id="choice1">
                      <option value="meeting room">meeting room</option>/>
                      <option value="desk">desk</option>/>
                      <option value="private office room">private office room</option>/>
                    </datalist>
                </div>

               
                                
                <div className="field-container">
                    <label>Capacity (Number of seats - range)</label><br></br>
                    <input type="number" data-test="CapacitySeatsFrom" value={this.state.CapacitySeatsFrom} onChange={this.handleCapacitySeatsFrom}/>
                    <span>&nbsp;</span>
                    <input type="number" data-test="CapacitySeatsTo" value={this.state.CapacitySeatsTo}  onChange={this.handleCapacitySeatsTo}/>
                </div>
                
                
                <div className="field-container">
                    <button className="buttonSubmit" onClick={this.Search}>Search</button>
                </div>
           
                              
            </div>
                </div>
              </div>
            
      );
    }
}

export default RenterDash;