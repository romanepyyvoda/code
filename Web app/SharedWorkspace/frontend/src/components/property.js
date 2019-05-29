import React, { Component } from 'react';
import PropertyCRUD from './propertyCRUD';
import '../css/style.css';

class Property extends Component{
  constructor(props){
		super(props)
    this.state = {
      properties:[],
      error: null,
    }
  }
    componentDidMount(){
      this.getProperties();
    }
      
    getProperties = _ =>{
      console.log("get properties method: " + this.props.owneridprops);

      fetch(`http://localhost:5002/property-listing-page/${this.props.owneridprops}`)
      .then(response => response.json())
      .then(response => this.setState({properties : response}))
      .catch(error => this.setState({ error }));
    }
  
    
    renderProperty = ({PropertyID,  FloorSpaceSQFT, NeighbourhoodName, StreetNumber, StreetName, City, PostalCode, ParkingAvailability,PublicTransportation}) => <div key={PropertyID} > PropertyID: {PropertyID}. -- {FloorSpaceSQFT}sqft, &nbsp;{NeighbourhoodName} - {StreetNumber}, {StreetName}, {City}, {PostalCode}, &nbsp;Parking - {ParkingAvailability}, &nbsp;Public transport - {PublicTransportation}</div>
  
     render() {
      const { properties } = this.state;
  
      return (
          <div>
              <h2 >My Properties</h2>
              <div className="wrapper">
                <div className="left">
                <h2>List of Properties:</h2>
                <div>{properties.map(this.renderProperty)}</div>
                </div>
                <div className="right"><PropertyCRUD getPropertiesProp = {this.getProperties} ownerIdProps = {this.props.owneridprops}/></div>
              </div>
                
                
          </div>
        );
      }
  }
  export default Property;

  