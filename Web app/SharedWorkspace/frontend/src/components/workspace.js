import React, { Component } from 'react';
import WorkspaceCRUD from './workspaceCRUD';
import '../css/style.css';

class Workspace extends Component{
    constructor(props){
      super(props);
    this.state = {
      workspaces:[],
      error: null,
    } 
  }
    componentDidMount(){
      this.getWorkspaces();
    }


    getWorkspaces = _ =>{
      fetch(`http://localhost:5002/workspace-listing-page/${this.props.owneridprops}`)
      .then(response => response.json())
      .then(response => this.setState({workspaces : response}))
      .catch(error => this.setState({ error }));
    }
  
    
    renderWorkspace = ({WorkspaceID,  WorkspaceType, DayPrice, WeekPrice, MonthPrice, CapacitySeats, Smoking}) => <div key={WorkspaceID} > WorkspaceID: {WorkspaceID}. -- {WorkspaceType}, ${DayPrice}/day, ${WeekPrice}/week, ${MonthPrice}/month, {CapacitySeats} seats, Smoking - {Smoking}</div>
  
     render() {
        const { workspaces } = this.state;
       
        return (
          <div>
              <h2>My Workspaces</h2>
            <div className="wrapper">
                <div className="left">
                <h2>List of Workspaces:</h2>
                <div>{workspaces.map(this.renderWorkspace)}</div>
                </div>
                <div className="right"><WorkspaceCRUD getWorkspacesProp={this.getWorkspaces}/></div>
              </div>
          </div>
        );
      }
  }
  export default Workspace;

  // class Workspace extends Component{
  //   constructor(){
  //     super();
  //   this.state = {
  //     workspaces:[],
  //     isLoading: false,
  //     error: null,
  //   } 
  // }
  //   componentDidMount(){
  //     this.setState({ isLoading: true });
  //     this.getWorkspaces();
  //   }
  //   getWorkspaces = _ =>{
  //     fetch('http://localhost:3001/workspace-listing-page/')
  //     .then(response => response.json())
  //     .then(response => this.setState({workspaces : response,  isLoading: false}))
  //     .catch(error => this.setState({ error, isLoading: false }));
  //   }
  
    
  //   renderWorkspace = ({WorkspaceID,  WorkspaceType, DayPrice, WeekPrice, MonthPrice, CapacitySeats, Smoking}) => <div key={WorkspaceID} > {WorkspaceID}: {WorkspaceType}, ${DayPrice}/day, ${WeekPrice}/week, ${MonthPrice}/month, {CapacitySeats} seats, Smoking - {Smoking}</div>
  
  //    render() {
  //     const { workspaces, isLoading } = this.state;
  
  //     if (isLoading) {
  //       return <p>Loading ...</p>;
  //     }
  //       return (
  //         <div>
  //             <h2>My Workspaces</h2>
  //             {workspaces.map(this.renderWorkspace)}
  //           <WorkspaceCRUD/>
  //         </div>
  //       );
  //     }
  // }
  // export default Workspace;