/*-----global-----*/
const Component = window.React.Component;
const BrowserRouter = window.ReactRouterDOM.BrowserRouter;
const Route =  window.ReactRouterDOM.Route;
const Link =  window.ReactRouterDOM.Link;
const Prompt =  window.ReactRouterDOM.Prompt;
const Switch = window.ReactRouterDOM.Switch;
const Redirect = window.ReactRouterDOM.Redirect;
const ReactTable = window.ReactTable.default

/*-----MAIN APP-----*/
	class App extends React.Component {
		render() {
			return (
				<div>
					<Header />
					<Main />
					<Footer />
				</div>
			);
		}
	}
	
	class Uploader extends React.Component {
		constructor(props) {
			super(props);
			this.state = {file: '',imagePreviewUrl: ''};
		}

		_handleSubmit(e) {
			e.preventDefault();
			// TODO: do something with -> this.state.file
			console.log('handle uploading-', this.state.file);
			
			var data = new FormData();
			data.append("file", this.state.file);
			data.append("name", this.state.file.name);

			fetch("http://localhost:8080/upload", {
				credentials: 'same-origin',
				mode: 'no-cors',
				method: "POST",
				headers: {
				"Accept": "application/json",
				"type": "formData"
				},
				body: data
			}).then(function (response) {
				if (response.ok) {
					alert("OK! ");
				} else if (response.status == 401) {
					alert("401 ");
				}
				}, function (e) {
					alert("Error submitting form!");
			});
		}

		_handleImageChange(e) {
			e.preventDefault();

			let reader = new FileReader();
			let file = e.target.files[0];

			reader.onloadend = () => {
				this.setState({
				file: file,
				imagePreviewUrl: reader.result
				});
			}

			reader.readAsDataURL(file)
		}

		render() {
			let {imagePreviewUrl} = this.state;
			let $imagePreview = null;
			if (imagePreviewUrl) {
				$imagePreview = (<img src={imagePreviewUrl} />);
			} else {
				$imagePreview = (<div className="previewText">Please select an Image for Preview</div>);
			}

			return (
				<div className="previewComponent">
					<form onSubmit={(e)=>this._handleSubmit(e)}>
						<input className="fileInput" type="file" onChange={(e)=>this._handleImageChange(e)} />
						<button className="submitButton" type="submit" onClick={(e)=>this._handleSubmit(e)}>Upload Image</button>
					</form>
					<div className="imgPreview">
						{$imagePreview}
					</div>
				</div>
			)
		}
	}
	
/*-----COMMON PAGE COMPONENTS-----*/
	/*-----Header-----*/
		class Header extends Component {
			render() {
				return (
					<header>
						<a href="#" id="logo"></a>
						<nav>
							<ul id="navControl">
								<li><a href="/login">login</a></li>
								<li><a href="/logout">logout</a></li>
							</ul>
							<ul id="navMain">
								<li><Link to="/">frontpage</Link></li>
								<li><Link to="/browse">browse</Link></li>
								<li><Link to="/upload">upload(user)</Link></li>
								<li><Link to="/manage">manage(admin)</Link></li>
							</ul>
						</nav>
						<div className="clear"></div>
					</header>
				);
			}
		}

	/*-----Footer-----*/
		class Footer extends Component {
			render() {
				return (
					<footer>
						<p>Kim Brygger (c)</p>
					</footer>
				);
			}
		}

/*-----PAGE CONTENT-----*/
	class Main extends React.Component {
		render() {
			return (
				<main>
					<Switch>
						<Route exact path='/' component={Home}/>
						<Route exact path='/browse' component={Browse}/>
						<Route exact path='/upload' component={Upload}/>
						<Route exact path='/manage' component={Manage}/>
					</Switch>
				</main>
			);
		}
	}
	/*-----Home Page-----*/
		class Home extends Component {
			render() {
				return (
					<div>
						<h2>Welcome</h2>
						<p>Welcome text, information of the site</p>
					</div>
				);
			}
		}

	/*-----Browse Page-----*/
		class Browse extends Component {
			render() {
				return (
					<div>
						<h2>Browsing page</h2>
						<p>images all users READ ONLY</p>
					</div>
				);
			}
		}
	/*-----Upload page-----*/
		class Upload extends React.Component {
			render() {
				return (
					<div>
						<h2>Upload</h2>
						<p>Registered USER.level permissions <br />Read,Create</p>
						<Uploader />
					</div>
				)
			}
		}
	/*-----Manage page-----*/
		class Manage extends React.Component {
		  constructor(props) {
			  super(props);
			  this.deleteImage = this.deleteImage.bind(this);
			  this.createImage = this.createImage.bind(this);
			  this.state = {
				  images: [],
			  };
		   }
		 
		  componentDidMount() {
			this.loadImagesFromServer();
		  }
		  
		  // Load images from database
		  loadImagesFromServer() {
			  fetch('http://localhost:8080/api/images', {credentials: 'same-origin'}) 
			  .then((response) => response.json()) 
			  .then((responseData) => { 
				  this.setState({ 
					  images: responseData._embedded.images, 
				  }); 
			  });     
		  } 
		  
		  // Delete image
		  deleteImage(image) {
			  fetch (image._links.self.href,
			  {method: 'DELETE', credentials: 'same-origin'})
			  .then( 
				  res => this.loadImagesFromServer()
			  )             
		  }  
		  
		  // Create new image
		  createImage(image) {
			  fetch('http://localhost:8080/api/images', {
				  method: 'POST', credentials: 'same-origin',
				  headers: {
					'Content-Type': 'application/json',
				  },
				  body: JSON.stringify(image)
			  })
			  .then( 
				  res => this.loadImagesFromServer()
			  )
		  }
		  
		  render() {
			return (
			   <div>
				  <ImageForm createImage={this.createImage}/>
				  <ImageTable deleteImage={this.deleteImage} images={this.state.images}/> 
			   </div>
			);
		  }
		}
				
		class ImageTable extends React.Component {
			constructor(props) {
				super(props);
			}
			
			render() {
			var images = this.props.images.map(image =>
				<Image key={image._links.self.href} image={image} deleteImage={this.props.deleteImage}/>
			);

			return (
			  <div>
			  <table className="table table-striped">
				<thead>
				  <tr>
					<th>Game</th>
					<th>Name</th>
					<th>Date</th>
					<th>url</th>
					<th>tags</th>
					<th></th>
				  </tr>
				</thead>
				<tbody>{images}</tbody>
			  </table>
			  </div>);
		  }
		}
				
		class Image extends React.Component {
			constructor(props) {
				super(props);
				this.deleteImage = this.deleteImage.bind(this);
			}

			deleteImage() {
				this.props.deleteImage(this.props.image);
			} 
		 
			render() {
				return (
				  <tr>
					<td>{this.props.image.game}</td>
					<td>{this.props.image.name}</td>
					<td>{this.props.image.date}</td>
					<td>{this.props.image.url}</td>
					<td>{this.props.image.tags}</td>
					<td>
						<button className="btn btn-danger" onClick={this.deleteImage}>Delete</button>
					</td>
				  </tr>
				);
			} 
		}

		class ImageForm extends React.Component {
			constructor(props) {
				super(props);
				this.state = {
					game: '',
					name: '',
					date: '',
					url: '',
					tags: ''
				};
				this.handleSubmit = this.handleSubmit.bind(this);   
				this.handleChange = this.handleChange.bind(this);     
			}

			handleChange(event) {
				this.setState(
					{[event.target.name]: event.target.value}
				);
			}    
			
			handleSubmit(event) {
				event.preventDefault();
				var newImage = {
					game: this.state.game,
					name: this.state.name,
					date: this.state.date,
					url: this.state.url,
					tags: this.state.tags
				};
				this.props.createImage(newImage);        
			}
			
			render() {
				return (
					<div className="panel panel-default">
						Create image
						<form className="form-inline">
							<div className="col-md-2">
								<input type="text" placeholder="Game" className="form-control"  name="game" onChange={this.handleChange}/>
							
								<input type="text" placeholder="tags" className="form-control" name="tags" onChange={this.handleChange}/>
								
								<Uploader />
								
								<button className="btn btn-success" onClick={this.handleSubmit}>Save</button>   
							</div>        
						</form>   
					</div>
				 
				);
			}
		}

/*----RENDER----*/
ReactDOM.render((
	<BrowserRouter>
		<App />
	</BrowserRouter>
	), document.getElementById('root')
);