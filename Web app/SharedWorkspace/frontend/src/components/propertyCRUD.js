import React, { Component } from 'react';
import axios from 'axios';
import '../css/style.css';

class PropertyCRUD extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedOption: '',
            enabledPropertyID: true,
            enabledAllOtherFields: true,
            user_id: '',
            property_id:'',
            floor_space:'',
            neighbourhood_name:'',
            street_number:'',
            street_name:'',
            city: '',
            postal_code: '',
            parking_availability: '',
            public_transportation: '',
            error: ''         
        };
   
        this.handleSubmit = this.handleSubmit.bind(this);
        this.dismissError = this.dismissError.bind(this);
        this.handlePropertyIDChange = this.handlePropertyIDChange.bind(this);
        this.handleFloorChange = this.handleFloorChange.bind(this);
        this.handleNeighbourChange = this.handleNeighbourChange.bind(this);
        this.handleStreetNumberChange = this.handleStreetNumberChange.bind(this);
        this.handleStreetNameChange = this.handleStreetNameChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handlePostalCodeChange = this.handlePostalCodeChange.bind(this);
        this.handleParkingChange = this.handleParkingChange.bind(this);
        this.handleTransportChange = this.handleTransportChange.bind(this);
        this.clearFields = this.clearFields.bind(this);
        this.resetFields = this.resetFields.bind(this);
        this.resetValue = this.resetValue.bind(this);
    }

    componentDidUpdate () {
        if(this.state.selectedOption === "add")
        {
            console.log("1:" +this.state.selectedOption + this.state.enabledPropertyID + this.state.enabledAllOtherFields);
            this.clearFields();
            if(this.state.enabledPropertyID){
                this.resetValue();
                this.setState({
                    enabledPropertyID: false,
                });
            }
            if(!this.state.enabledAllOtherFields){
                this.setState({
                    enabledAllOtherFields: true
                });
            }
            this.resetFields();
        }
        else if(this.state.selectedOption === "update")
        {
            console.log("2:" +this.state.selectedOption + this.state.enabledPropertyID + this.state.enabledAllOtherFields);
            this.clearFields();
            if(!this.state.enabledPropertyID){
                this.resetValue();
                this.setState({
                    enabledPropertyID: true,
                });
            }
            if(!this.state.enabledAllOtherFields){
                this.setState({
                    enabledAllOtherFields: true
                });
            }
            this.resetFields();
        }
        else if(this.state.selectedOption === "delete")
        {
            console.log("3:" +this.state.selectedOption + this.state.enabledPropertyID + this.state.enabledAllOtherFields);
            this.clearFields();
            if(!this.state.enabledPropertyID){
                this.resetValue(); 
                this.setState({
                    enabledPropertyID: true,
                });
            }
            if(this.state.enabledAllOtherFields){
                this.setState({
                    enabledAllOtherFields: false
                });
            }
            this.resetFields(); 
        }
        console.log("4:" +this.state.selectedOption + this.state.enabledPropertyID + this.state.enabledAllOtherFields);
        console.log();

    }

    clearFields(){
        this.refs.pid.value = ""; 
        this.refs.fs.value = ""; 
        this.refs.nn.value = ""; 
        this.refs.sn.value = ""; 
        this.refs.sname.value = ""; 
        this.refs.city.value = ""; 
        this.refs.pc.value = ""; 
        this.refs.pa.value = ""; 
        this.refs.pt.value = ""; 
    }
    resetFields(){
        this.refs.pid.value = this.state.property_id; 
        this.refs.fs.value = this.state.floor_space; 
        this.refs.nn.value = this.state.neighbourhood_name; 
        this.refs.sn.value = this.state.street_number; 
        this.refs.sname.value = this.state.street_name; 
        this.refs.city.value = this.state.city; 
        this.refs.pc.value = this.state.postal_code; 
        this.refs.pa.value = this.state.parking_availability; 
        this.refs.pt.value = this.state.public_transportation; 
    }
    resetValue(){
        this.setState({
            user_id: '',
            property_id:'',
            floor_space:'',
            neighbourhood_name:'',
            street_number:'',
            street_name:'',
            city: '',
            postal_code: '',
            parking_availability: '',
            public_transportation: '',
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
  
  
    handleFloorChange(evt) {
        this.setState({
          floor_space: evt.target.value,
        });
    }
  
    handleNeighbourChange(evt) {
      this.setState({
        neighbourhood_name: evt.target.value,
      });
    };
    
    handleStreetNumberChange(evt) {
        this.setState({
          street_number: evt.target.value,
        });
    }
  
    handleStreetNameChange(evt) {
      this.setState({
        street_name: evt.target.value,
      });
    };
    
    handleCityChange(evt) {
      this.setState({
        city: evt.target.value,
      });
    }
  
    handlePostalCodeChange(evt) {
      this.setState({
        postal_code: evt.target.value,
      });
    };

    handleParkingChange(evt) {
        this.setState({
          parking_availability: evt.target.value,
        });
    };

    handleTransportChange(evt) {
        this.setState({
          public_transportation: evt.target.value,
        });
    };

    
    handleSubmit(evt) {
        evt.preventDefault();
        
        if (!this.state.property_id && !(this.state.selectedOption === "add")) {
          return this.setState({ error: 'Property ID is required' });
        }
        
        if (!this.state.floor_space && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
            return this.setState({ error: 'Floor Space is required' });
        }
    
        if (!this.state.neighbourhood_name && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Neighbourhood Name is required' });
        }
    
        if (!this.state.street_number && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Street Number is required' });
        }
    
        if (!this.state.street_name && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Street Name is required' });
        }
        
        if (!this.state.city && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'City is required' });
        }
    
        if (!this.state.postal_code && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Postal Code is required' });
        }

        if (!this.state.parking_availability && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
            return this.setState({ error: 'Parking availability is required' });
        }
        
        if (!this.state.public_transportation && (!(this.state.selectedOption === "update") && !(this.state.selectedOption === "delete"))) {
          return this.setState({ error: 'Public transportation is required' });
        }
        
        
        this.setState({ error: '' });
        if(this.state.selectedOption === "add")
        {
            axios.post('http://localhost:5002/add-property', {
                user_id: this.props.ownerIdProps,
                //property_id: this.state.property_id,
                floor_space: this.state.floor_space,
                neighbourhood_name: this.state.neighbourhood_name,
                street_number: this.state.street_number,
                street_name: this.state.street_name,
                city: this.state.city,
                postal_code: this.state.postal_code,
                parking_availability: this.state.parking_availability,
                public_transportation: this.state.public_transportation,
            })
            .then(res => {
                if(res.data === 403){
                return this.setState({ error: 'Something went wrong!' });
                }
                if(res.data === 'success'){
                    this.props.getPropertiesProp();
                }
            })
            .catch(error => {
                console.log(error);
                return this.setState({ error: 'Something went wrong!' });
            });
        }
        if(this.state.selectedOption === "update")
        {
            axios.put('http://localhost:5002/update-property', {
                property_id: this.state.property_id,
                floor_space: this.state.floor_space,
                neighbourhood_name: this.state.neighbourhood_name,
                street_number: this.state.street_number,
                street_name: this.state.street_name,
                city: this.state.city,
                postal_code: this.state.postal_code,
                parking_availability: this.state.parking_availability,
                public_transportation: this.state.public_transportation,
            })
            .then(res => {
                if(res.data === 403){
                return this.setState({ error: 'Something went wrong!' });
                }
                if(res.data === 'success'){
                    this.props.getPropertiesProp();
                }
            })
            .catch(error => {
                console.log(error);
                return this.setState({ error: 'Something went wrong!' });
            });
        }
        if(this.state.selectedOption === "delete")
        {
            axios.delete(`http://localhost:5002/delete-property/${this.state.property_id}`)
            .then(res => {
                if(res.data === 403){
                return this.setState({ error: 'Something went wrong!' });
                }
                if(res.data === 'success'){
                    this.props.getPropertiesProp();
                }
            })
            .catch(error => {
                console.log(error);
                return this.setState({ error: 'Something went wrong!' });
            });
        }
        this.props.getPropertiesProp();
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
                        <input type="radio" value="delete" checked={this.state.selectedOption === 'delete'} onChange={(e) => this.setState({ selectedOption: e.target.value})} />
                        Delete
                    </label>
                </div>

                <div className="field-container">
                    <label>Property ID</label><br></br>
                    <input type="number" data-test="PropertyID" ref="pid" value={this.state.property_id} disabled={!(this.state.enabledPropertyID)} onChange={this.handlePropertyIDChange}/>
                </div>

                <div className="field-container">
                    <label>Floor Space, sq.ft</label><br></br>
                    <input type="number" data-test="FloorSpaceSQFT" ref="fs" value={this.state.floor_space} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleFloorChange}/>
                </div>
                                
                <div className="field-container">
                    <label>Neighbourhood Name</label><br></br>
                    <input type="text" data-test="NeighbourhoodName" ref="nn" value={this.state.neighbourhood_name} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleNeighbourChange}/>
                </div>
                
                <div className="field-container">
                    <label>Street Number</label><br></br>
                    <input type="text" data-test="StreetNumber" ref="sn" value={this.state.street_number} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleStreetNumberChange}/>
                </div>
                                
                <div className="field-container">
                    <label>Street Name</label><br></br>
                    <input type="text" data-test="StreetName" ref="sname" value={this.state.street_name} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleStreetNameChange}/>
                </div>

                <div className="field-container">
                    <label>City</label><br></br>
                    <input type="text" data-test="City" ref="city" value={this.state.city} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleCityChange}/>
                </div>
                                
                <div className="field-container">
                    <label>Postal Code</label><br></br>
                    <input type="text" data-test="PostalCode" ref="pc" value={this.state.postal_code} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handlePostalCodeChange}/>
                </div>

                <div className="field-container">
                    <label>Parking</label><br></br>
                    <input  list="choice" type="text" data-test="ParkingAvailability" ref="pa" value={this.state.parking_availability} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleParkingChange}/>
                    <datalist id="choice">
                      <option value="yes">yes</option>/>
                      <option value="no">no</option>/>
                    </datalist>
                </div>
                                
                <div className="field-container">
                    <label>Public Transportation</label><br></br>
                    <input list="choice" type="text" data-test="PublicTransportation" ref="pt" value={this.state.public_transportation} disabled={!(this.state.enabledAllOtherFields)} onChange={this.handleTransportChange}/>
                </div>

                <div className="field-container">
                    <button className="buttonSubmit" onClick="">Submit</button>
                </div>
            </form>
                              
            </div>
      );
    }
}

export default PropertyCRUD;