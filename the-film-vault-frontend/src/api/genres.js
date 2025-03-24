const GENRES_API = "http://localhost:8080/api/genres";

export async function fetchGenres() {
  try {
    const response = await fetch(GENRES_API);
    return await response.json();
  } catch (error) {
    console.error("Error fetching genres:", error);
    return [];
  }
}
