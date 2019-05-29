import React, { Component } from 'react';
import axios from 'axios';
import '../css/style.css';

class WorkspaceCRUD extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedOption: '',
            enabledWorkspaceID: true,
            enabledAllOtherFields: true,
            workspace_id: '',
            property_id: '',
            workspace_type: '',
            per_day:'',
            per_week:'',
            per_month: '',
            capacity:'',
            smoking:'',
            error: ''  
        };
        
        this.handleSubmit = this.handleSubmit.bind(this);
        this.dismissError = this.dismissError.bind(this);
        this.handlePropertyIDChange = this.handlePropertyIDChange.bind(this);
        this.handleWorkspaceIDChange = this.handleWorkspaceIDChange.bind(this);
        this.handleWorkspaceTypeChange = this.handleWorkspaceTypeChange.bind(this);
        this.handlePerDayChange = this.handlePerDayChange.bind(this);
        this.handlePerWeekChange = this.handlePerWeekChange.bind(this);
        this.handlePerMonthChange = this.handlePerMonthChange.bind(this);
        this.handleCapacityChange = this.handleCapacityChange.bind(this);
        this.handleSmokingChange = this.handleSmokingChange.bind(this);
        this.clearFields = this.clearFields.bind(this);
        this.resetFields = this.resetFields.bind(this);
        this.resetValue = this.resetValue.bind(this);
    
    }

    
    componentDidUpdate(){
        if(this.state.selectedOption === "add")
        {
            
            console.log("add1 --" +this.state.selectedOption + this.state.enabledWorkspaceID + this.state.enabledAllOtherFields);
            this.clearFields();
            if(this.state.enabledWorkspaceID){
                this.resetValue();
                this.setState({
                    enabledWorkspaceID: false,
                });
            }
            if(!this.state.enabledAllOtherFields){
                this.resetValue();
                this.setState({
                    enabledAllOtherFields: true
                });
            }
            this.resetFields();
        }
        else if(this.state.selectedOption === "update")
        {
            console.log("update1 --" + this.state.selectedOption + this.state.enabledWorkspaceID + this.state.enabledAllOtherFields);
            this.clearFields();
            if(!this.state.enabledWorkspaceID){
                this.resetValue();
                this.setState({
                    enabledWorkspaceID: true,
                });
            }
            if(!this.state.enabledAllOtherFields){
                this.resetValue();
                this.setState({
                    enabledAllOtherFields: true
                });
            }
            this.resetFields();
        }
        else if(this.state.selectedOption === "delete")
        {
            console.log("delete1 --" + this.state.selectedOption + this.state.enabledWorkspaceID + this.state.enabledAllOtherFields);
            this.clearFields();
            if(!this.state.enabledWorkspaceID){
                this.resetValue();
                this.setState({
                    enabledWorkspaceID: true,
                });
            }
            if(this.state.enabledAllOtherFields){
                this.resetValue();
                this.setState({
                    enabledAllOtherFields: false
                });
            }
            this.resetFields();
        }
    }

    clearFields(){
        this.refs.pid.value = ""; 
        this.refs.wid.value = ""; 
        this.refs.wt.value = ""; 
        this.refs.pd.value = ""; 
        this.refs.pw.value = ""; 
        this.refs.pm.value = ""; 
        this.refs.ca.value = ""; 
        this.refs.sm.value = ""; 
    }
    resetFields(){
        this.refs.pid.value = this.state.property_id; 
        this.refs.wid.value = this.state.workspace_id; 
        this.refs.wt.value = this.state.workspace_type; 
        this.refs.pd.value = this.state.per_day; 
        this.refs.pw.value = this.state.per_week; 
        this.refs.pm.value = this.state.per_month; 
        this.refs.ca.value = this.state.capacity; 
        this.refs.sm.value = this.state.smoking; 
    }
    resetValue(){
        this.setState({
            property_id: '',
            workspace_id: '',
            workspace_type: '',
            per_day:'',
            per_week:'',
            per_month: '',
            capacity:'',
            smoking:'',
            error: '' 
        });
    }

    dismissError() {
        this.setState({ error: '' });
    }

    handlePropertyIDChange(evt) {
        this.setState({
            property_id: evt.target.value,
        });
      };

    handleWorkspaceIDChange(evt) {
        this.setState({
            workspace_id: evt.target.value,
        });
      };

      handleWorkspaceTypeChange(evt) {
        this.setState({
            workspace_type: evt.target.value,
        });
      };

      handlePerDayChange(evt) {
        this.setState({
            per_day: evt.target.value,
        });
      };

      handlePerWeekChange(evt) {
        this.setState({
            per_week: evt.target.value,
        });
      };

      handlePerMonthChange(evt) {
        this.setState({
            per_month: evt.target.value,
        });
      };
      
      handleCapacityChange(evt) {
        this.setState({
            capacity: evt.target.value,
        });
      };

      handleSmokingChange(evt) {
        this.setState({
            smoking: evt.target.value,
        });
      };

      handleSubmit(evt) {
        evt.preventDefault();
        
        if (!this.state.workspace_id && !(this.state.selectedOption === "add")) {
          return this.setState({ error: 'Workspace ID is required' });
        }

        if (!this.state.property_id && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
            return this.setState({ error: 'Property ID is required' });
        }
        
        if (!this.state.workspace_type && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
            return this.setState({ error: 'Workspace Type is required' });
        }
    
        if (!this.state.per_day && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Day Price is required' });
        }
    
        if (!this.state.per_week && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Week Price is required' });
        }
    
        if (!this.state.per_month && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Month Price is required' });
        }
        
        if (!this.state.capacity && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Capacity Seats is required' });
        }
    
        if (!this.state.smoking && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Smoking field is required' });
        }

        this.setState({ error: '' });
        
        var wtid = 1;
        if(this.state.workspace_type === "meeting room"){ wtid = 1;}
        if(this.state.workspace_type === "private office room"){ wtid = 2;}
        if(this.state.workspace_type === "desk"){ wtid = 3;}
        
        if(this.state.selectedOption === "add")
        {
            axios.post('http://localhost:5002/add-workspace', {
                property_id: this.state.property_id,
                workspace_type_id: wtid,
                per_day: this.state.per_day,
                per_week: this.state.per_week,
                per_month: this.state.per_month,
                capacity: this.state.capacity,
                smoking: this.state.smoking,
            })
            .then(res => {
                console.log("add response 1: " + res.data);
                if(res.data === 403){
                return this.setState({ error: 'Something went wrong!' });
                }
                if(res.data === 'success'){
                    console.log("add response 2: " + res.data);
                    this.props.getWorkspacesProp();
                }
            })
            .catch(error => {
                console.log(error);
                return this.setState({ error: 'Something went wrong!' });
            });
        }
        if(this.state.selectedOption === "update")
        {
            axios.put('http://localhost:5002/update-workspace', {
                workspace_id: this.state.workspace_id,
                property_id: this.state.property_id,
                workspace_type_id: wtid,
                per_day: this.state.per_day,
                per_week: this.state.per_week,
                per_month: this.state.per_month,
                capacity: this.state.capacity,
                smoking: this.state.smoking,
            })
            .then(res => {
                if(res.data === 403){
                return this.setState({ error: 'Something went wrong!' });
                }
                if(res.data === 'success'){
                    this.props.getWorkspacesProp();
                }
            })
            .catch(error => {
                console.log(error);
                return this.setState({ error: 'Something went wrong!' });
            });
        }
        if(this.state.selectedOption === "delete")
        {
            axios.delete(`http://localhost:5002/delete-workspace/${this.state.workspace_id}`)
            .then(res => {
                if(res.data === 403){
                return this.setState({ error: 'Something went wrong!' });
                }
                if(res.data === 'success'){
                    this.props.getWorkspacesProp();
                }
            })
            .catch(error => {
                console.log(error);
                return this.setState({ error: 'Something went wrong!' });
            });
        }
        this.props.getWorkspacesProp();
        this.clearFields();
        this.resetValue();
        this.resetFields();
      }   



    render() {
        return (
            <div className="main-container">
              <form onSubmit={this.handleSubmit}>
                {
                  this.state.error &&
                  <div className="error-box" data-test="error" onClick={this.dismissError}>
                    <button className="buttonClose" onClick={this.dismissError}>✖</button>
                    {this.state.error}
                  </div>
                }
                
                <div className="radio">
                    <label>
                        <input type="radio" value="add" checked={this.state.selectedOption === 'add'} onChange={(e) => this.setState({ selectedOption: e.target.value })} />
                        Add
                        <input type="radio" value="update" checked={this.state.selectedOption === 'update'} onChange={(e) => this.setState({ selectedOption: e.target.value })} />
                        Update
                        <input type="radio" value="delete" checked={this.state.selectedOption === 'delete'} onChange={(e) => this.setState({ selectedOption: e.target.value })} />
                        Delete
                    </label>
                </div>
              

                <div className="field-container">
                    <label>Workspace ID</label><br></br>
                    <input type="number" data-test="WorkspaceID" ref="wid" value={this.state.workspace_id} disabled={!(this.state.enabledWorkspaceID)} onChange={this.handleWorkspaceIDChange}/>
                </div>

                <div className="field-container">
                    <label>Property ID</label><br></br>
                    <input type="number" data-test="PropertyID" ref="pid" value={this.state.property_id} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handlePropertyIDChange}/>
                </div>

                <div className="field-container">
                    <label>Workspace Type</label><br></br>
                    <input list="choice1" type="text" data-test="WorkspaceType" ref="wt" value={this.state.workspace_type} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleWorkspaceTypeChange}/>
                    <datalist id="choice1">
                      <option value="meeting room">meeting room</option>/>
                      <option value="desk">desk</option>/>
                      <option value="private office room">private office room</option>/>
                    </datalist>
                </div>
                                
                <div className="field-container">
                    <label>Day Price, $CAD</label><br></br>
                    <input type="text" data-test="DayPrice" ref="pd" value={this.state.per_day} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handlePerDayChange}/>
                </div>
                
                <div className="field-container">
                    <label>Week Price, $CAD</label><br></br>
                    <input type="text" data-test="WeekPrice" ref="pw" value={this.state.per_week} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handlePerWeekChange}/>
                </div>
                                
                <div className="field-container">
                    <label>Month Price, $CAD</label><br></br>
                    <input type="text" data-test="MonthPrice" ref="pm" value={this.state.per_month} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handlePerMonthChange}/>
                </div>

                <div className="field-container">
                    <label>Capacity Seats</label><br></br>
                    <input type="text" data-test="CapacitySeats" ref="ca" value={this.state.capacity} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleCapacityChange}/>
                </div>
                                
                <div className="field-container">
                    <label>Smoking</label><br></br>
                    <input  list="choice2" type="text" data-test="Smoking" ref="sm" value={this.state.smoking} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleSmokingChange}/>
                    <datalist id="choice2">
                      <option value="yes">yes</option>/>
                      <option value="no">no</option>/>
                    </datalist>
                </div>
                
                <div className="field-container">
                    <button className="buttonSubmit" onClick="">Submit</button>
                </div>
            </form>
                              
            </div>
      );
    }
}

export default WorkspaceCRUD;