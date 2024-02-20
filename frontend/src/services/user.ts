//const baseURL = "https://matchguessr.azurewebsites.net/auth"
const baseURL = "http://localhost:8080/auth"

const register = async (username: string, password: string) => {
  const response = await fetch(`${baseURL}/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  });
  const data = await response.json();
  return data.token as string;
}

const login = async (username: string, password: string) => {
  const response = await fetch(`${baseURL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  });

  const data = await response.json();
  return data.token as string;
}

const verify = async (token: string) => {
  const response = await fetch(`${baseURL}/validate`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
  });

  return response.status === 200;
}

export default { register, login, verify };