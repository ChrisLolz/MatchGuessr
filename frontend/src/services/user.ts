// const baseURL = "https://matchguessr.azurewebsites.net/auth"
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

export default { register, login }