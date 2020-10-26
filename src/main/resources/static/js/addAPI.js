const createDiv = (mClass, id, name, path, method, body) => {
  let infoDiv = document.createElement("div")
  infoDiv.setAttribute("id", id)
  infoDiv.setAttribute("class", mClass)
  title = document.createElement("h3")
  title.innerHTML = name
  content = "<br>"
  content += "<b>Path: </b>" + path
  content += "<br>"
  content += "<b>Method: </b>" + method
  content += "<br>"
  content += "<b>Body: </b>" + body
  info = document.createElement("p")
  info.innerHTML = content
  infoDiv.appendChild(title)
  infoDiv.appendChild(info)
  return infoDiv
}

const login_paths = () => {
  const userPath = "/api/v1/user"
  loginDiv = document.getElementById("login")
  loginDiv.appendChild(createDiv("col-md-4 box", "user-register", "Register a new user", userPath+"/signup", "POST", "username, password"))
  loginDiv.appendChild(createDiv("col-md-4 box", "user-login", "Get Token", userPath+"/login", "POST", "username, password"))
}
const customer_paths = () => {
  const userPath = "/api/v1/customer"
  loginDiv = document.getElementById("customer")
  loginDiv.appendChild(createDiv("col-md-4 box", "customer-all", "Get all customers", userPath+"/all", "GET", ""))
  loginDiv.appendChild(createDiv("col-md-4 box", "customer-register", "Register a NEW customer" , userPath+"/register", "POST", "customer_id, name, surname"))
  loginDiv.appendChild(createDiv("col-md-4 box", "customer-findID", "Find customer by ID" , userPath+"/find/{customer_id}", "GET", ""))
  loginDiv.appendChild(createDiv("col-md-4 box", "customer-findOther", "Find customer by name or surname" , userPath+"/find/", "GET", "Optional: [name], [surname]"))
  loginDiv.appendChild(createDiv("col-md-4 box", "customer-update", "Update an existing customer" , userPath+"/update", "PUT", "customer_id, name, surname"))
}
const creditcard_paths = () => {
  const userPath = "/api/v1/creditcard"
  loginDiv = document.getElementById("creditcard")
  loginDiv.appendChild(createDiv("col-md-4 box", "creditcard-all", "Get all creditcards", userPath+"/all", "GET", ""))
  loginDiv.appendChild(createDiv("col-md-4 box", "creditcard-register", "Register a NEW credit of a customer" , userPath+"/register/{customer_id}", "POST", "numberCC, cvv, expiration_date"))
  loginDiv.appendChild(createDiv("col-md-4 box", "creditcard-findCustomer", "Find creditcard by customer" , userPath+"/find/customer/{customer_id}", "GET", ""))
  loginDiv.appendChild(createDiv("col-md-4 box", "creditcard-findNumber", "Find creditcard by number" , userPath+"/find/{customer_id}", "GET", ""))
  loginDiv.appendChild(createDiv("col-md-4 box", "creditcard-update", "Update an existing creditcard" , userPath+"/update/{customer_id}", "PUT", "numberCC, cvv, expiration_date"))
}
login_paths()
customer_paths()
creditcard_paths()