package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.Adress;
import model.entity.Animal;
import model.entity.UserAdopter;
import model.entity.UserOng;




public class Dao {
    private static final Logger LOGGER = Logger.getLogger(Dao.class.getName());

    private String getConfigValueByKey(String key) throws IOException {
        /*File file = new File("C:\\projetos\\miauDote.cao\\admin\\config.ini");  Utilizar esta linha para se conectar ao postgreSQL
         * 
         * 
         * UTILIZAR ESSES NO PC DO LUCAS
         *
         * 
         * Sempre alterar o caminho para o arquivo dependendo em qual computador está rodando*/
    	
    	
    	 //File file = new File("C:\\Users\\Joao Gabriel\\Desktop\\miaudote\\miauDote.cao\\admin\\configMySQL.ini");//Utilizar esta linha para conectar ao MySQL
    	File file = new File("C:\\Users\\Joao Gabriel\\Desktop\\backend\\MiauDoteCao\\admin\\configMySQL.ini"); //Utilizar para o MySQL
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2 && parts[0].equals(key)) {
                        return parts[1].trim();
                    }
                }
            }
        }
        return null;
    }

    public Connection connectDB() throws SQLException, IOException, ClassNotFoundException {
        String url = getConfigValueByKey("url");
        String user = getConfigValueByKey("user");
        String password = getConfigValueByKey("password");
        /*Class.forName("org.postgresql.Driver");  
         * Utilizar esta linha para se conectar com o postgreSQL
         * */
        Class.forName("com.mysql.jdbc.Driver"); //Utilizar esta linha para conectar ao MySQL

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to the database", e);
            throw e;
        }
        return connection;
    }
    
    public boolean registerUserOng(UserOng user, int idAdress) throws SQLException, ClassNotFoundException, IOException {
		// se precisar colocar public.userOng (em todas as tabelas que fazem transação)
        String sql = "INSERT INTO userOng (login, pw, username, cpf, birth, ongName, publicKey, privateKey, idAdress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = this.connectDB();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPw());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getCpf());
            statement.setString(5, user.getBirth());
            statement.setString(6, user.getOngName());
            statement.setString(7, user.getPublicKey());
            statement.setString(8, user.getPrivateKey());
            statement.setInt(9, idAdress);
            @SuppressWarnings("unused")
			int update = statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                @SuppressWarnings("unused")
				int id = keys.getInt(1);
                return true;
            } else {
                throw new SQLException("Nenhuma chave primaria foi gerada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro durante o registro: " + e.getMessage());
            return false;
        }
    }

	public boolean registerUserAdopter(UserAdopter user, int idAdress) throws SQLException, ClassNotFoundException, IOException {
		 String sql = "INSERT INTO userAdopter (login, pw, username, cpf, birth, publicKey, privateKey, idAdress) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection connection = this.connectDB();
	             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, user.getLogin());
	            statement.setString(2, user.getPw());
	            statement.setString(3, user.getUsername());
	            statement.setString(4, user.getCpf());
	            statement.setString(5, user.getBirth());
	            statement.setString(6, user.getPublicKey());
	            statement.setString(7, user.getPrivateKey());
	            statement.setInt(8, idAdress);
	            @SuppressWarnings("unused")
				int update = statement.executeUpdate();
	            ResultSet keys = statement.getGeneratedKeys();
	            if (keys.next()) {
	                @SuppressWarnings("unused")
					int id = keys.getInt(1);
	                return true;
	            } else {
	                throw new SQLException("Nenhuma chave primaria foi gerada.");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erro durante o registro: " + e.getMessage());
	            return false;
	        }
	    }

	public int registerAdress(Adress adress) throws ClassNotFoundException, IOException {
		String sql = "INSERT INTO adress (state, city, neighbor, cep, rua) VALUES (?,?,?,?,?)";
		//Adicionar  CAST (? AS INTEGER) no numero do endereco caso esteja conectando ao postgreSQL
		try (Connection connection = this.connectDB();
	             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, adress.getState());
			statement.setString(2, adress.getCity());
			statement.setString(3, adress.getNeighborhood());
			statement.setString(4, adress.getCep());
			statement.setString(5, adress.getStreet());
			//statement.setString(6, adress.getNumber());
			int update = statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				int id = keys.getInt(1);
				return id;
			}else {
				throw new SQLException("Nenhuma chave primaria foi gerada.");
			}
		}catch(SQLException e) {
			System.err.println("Erro durante o registro: "+e.getMessage());
			return -1;
		}
		
	}

	public boolean checkForDuplicityOngEmail(String login) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userOng WHERE login = ?";
		try (Connection conn = this.connectDB();
		         PreparedStatement statement = conn.prepareStatement(sql)) {
		        statement.setString(1, login);
		        ResultSet rs = statement.executeQuery();
		        return rs.next();
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return false;
		    }
		
		
	}

	public boolean checkForDuplicityOngName(String ongName) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userOng WHERE ongName = ?";
		try (Connection conn = this.connectDB();
		         PreparedStatement statement = conn.prepareStatement(sql)) {
		        statement.setString(1, ongName);
		        ResultSet rs = statement.executeQuery();
		        return rs.next();
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return false;
		    }
	}

	public boolean checkForDuplicityOngCPF(String cpf) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userOng WHERE cpf = ?";
		try (Connection conn = this.connectDB();
		         PreparedStatement statement = conn.prepareStatement(sql)) {
		        statement.setString(1, cpf);
		        ResultSet rs = statement.executeQuery();
		        return rs.next();
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return false;
		    }
	}
	public boolean checkForDuplicityAdopterEmail(String login) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userAdopter WHERE login = ?";
		try (Connection conn = this.connectDB();
		         PreparedStatement statement = conn.prepareStatement(sql)) {
		        statement.setString(1, login);
		        ResultSet rs = statement.executeQuery();
		        return rs.next();
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return false;
		    }
		
		
	}


	public boolean checkForDuplicityAdopterCPF(String cpf) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userAdopter WHERE cpf = ?";
		try (Connection conn = this.connectDB();
		         PreparedStatement statement = conn.prepareStatement(sql)) {
		        statement.setString(1, cpf);
		        ResultSet rs = statement.executeQuery();
		        return rs.next();
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		        return false;
		    }
	}

	public boolean validateLoginOng(String hashPassword, String login) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userOng WHERE login =? AND pw =?";
		try(Connection conn = this.connectDB();
				PreparedStatement statement = conn.prepareStatement(sql)){
					statement.setString(1, login);
					statement.setString(2, hashPassword);
					ResultSet rs = statement.executeQuery();
					return rs.next();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
		return false;
	}
	public boolean validateLoginAdopter(String hashPassword, String login) throws ClassNotFoundException, IOException {
		String sql = "SELECT * FROM userAdopter WHERE login =? AND pw =?";
		try(Connection conn = this.connectDB();
				PreparedStatement statement = conn.prepareStatement(sql)){
					statement.setString(1, login);
					statement.setString(2, hashPassword);
					ResultSet rs = statement.executeQuery();
					return rs.next();
				}catch(SQLException e) {
					System.out.println(e.getMessage());
				}
		return false;
	}

	public boolean insertAndUpdateJWT(String jwt, boolean isOng, String login) throws ClassNotFoundException, SQLException, IOException {
		String sql;
		if(isOng) {
			sql = "UPDATE userOng set jwt=? WHERE login=?";
		}else {
			sql = "UPDATE userAdopter set jwt=? WHERE login=?";
		}
		try(Connection conn = this.connectDB();
				PreparedStatement statement = conn.prepareStatement(sql)){
			statement.setString(1, jwt);
			statement.setString(2, login);
			int rowsUpdated = statement.executeUpdate();
			if(rowsUpdated == 0) {
				return false;
			}else {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public String getJWT(String login, boolean isOng) throws ClassNotFoundException, IOException {
	    String sql;
	    if (isOng) {
	        sql = "SELECT jwt FROM userOng WHERE login=?";
	    } else {
	        sql = "SELECT jwt FROM userAdopter WHERE login=?";
	    }
	    try (Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)) {
	        statement.setString(1, login);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            return rs.getString("jwt");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean adoptionApplication(String idAnimal, String idAdopter) throws ClassNotFoundException, IOException {
		String sql = "INSERT INTO animaladoption (idAnimal, idAdopter) VALUES (?,?)";
		 try (Connection connection = this.connectDB();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, idAnimal);
	            statement.setString(2, idAdopter);
	            @SuppressWarnings("unused")
				int update = statement.executeUpdate();
	            if(update > 0) {
	            	return true;
	            }else {
	            	return false;
	            }
	        } catch (SQLException e) {
	            System.err.println("Erro durante o registro: " + e.getMessage());
	            return false;
	        }
	    }

	public ArrayList<String> getOngName() throws SQLException, ClassNotFoundException, IOException {
		String sql = "SELECT ongName FROM userOng";
		ArrayList<String> ongs = new ArrayList<String>();
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				ongs.add(rs.getString(1));
			}
			return ongs;
		}
	}

	public ArrayList<Animal> selectAnimals(String race, String size, String hairType, String animalToAnimal, String animalToPerson,
			String sex, String age, String id, String city, String state) throws SQLException {
		String sql = "SELECT a.idAnimal, a.race, a.animalName, a.size, a.hairType, a.animalToAnimal, a.animalToPerson, " +
	             "a.sex, a.age, a.idOng, img.imageUrl " +
	             "FROM animal AS a " +
	             "JOIN userOng AS u ON a.idOng = u.idOng " +
	             "JOIN adress AS ad ON u.idAdress = ad.idAdress " +
	             "LEFT JOIN ( " +
	             "    SELECT idAnimal, imageUrl " +
	             "    FROM image " +
	             "    GROUP BY idAnimal, imageUrl " +
	             "    LIMIT 1 " +
	             ") AS img ON a.idAnimal = img.idAnimal " +
	             "WHERE ad.city = ? " +
	             "  AND ad.state = ? " +
	             "  AND a.race = ? " +
	             "  AND a.size = ? " +
	             "  AND a.hairType = ? " +
	             "  AND a.animalToAnimal = ? " +
	             "  AND a.animalToPerson = ? " +
	             "  AND a.sex = ? " +
	             "  AND a.age = ? " +
	             "GROUP BY a.idAnimal, a.race, a.animalName, a.size, a.hairType, a.animalToAnimal, a.animalToPerson, " +
	             "         a.sex, a.age, a.idOng, img.imageUrl " +
	             "ORDER BY a.insertionDate ASC " +
	             "LIMIT 10";
		ArrayList<Animal> animals = new ArrayList<Animal>();
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, city);
			statement.setString(2, state);
			statement.setString(3, race);
			statement.setString(4, size);
			statement.setString(5, hairType);
			statement.setString(6, animalToAnimal);
			statement.setString(7, animalToPerson);
			statement.setString(8, sex);
			statement.setString(9, age);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Animal a = new Animal();
					a.setId(rs.getString("idAnimal"));
					a.setRace(rs.getString("race"));
					a.setName(rs.getString("animalName"));
					a.setSize(rs.getString("size"));
					a.setHairType(rs.getString("hairType"));
					a.setAnimalToAnimal(rs.getString("animalToAnimal"));
					a.setAnimalToPerson(rs.getString("animalToPerson"));
					a.setSex(rs.getString("sex"));
					a.setAge(rs.getString("age"));
					a.setIdOng(rs.getString("idOng"));
					a.setImageUrl(rs.getString("imageUrl"));
					animals.add(a);
			}
			return animals;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Adress getUserAdress(String userId) throws ClassNotFoundException, IOException {
		String sql = "SELECT ad.city, ad.state " +
		        "FROM adress AS ad " +
		        "JOIN userOng AS u ON ad.idAdress = u.idAdress " +
		        "WHERE u.idOng = ?";
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)){
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				String city = rs.getString("city");
				String state = rs.getString("state");
				Adress a = new Adress();
				a.setCity(city);
				a.setState(state);
				return a;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
