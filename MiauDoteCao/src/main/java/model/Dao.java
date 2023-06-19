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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.Adress;
import model.entity.Animal;
import model.entity.UserAdopter;
import model.entity.UserOng;




public class Dao {
    private static final Logger LOGGER = Logger.getLogger(Dao.class.getName());

    private String getConfigValueByKey(String key) throws IOException {
    	File file = new File("C:\\Projetos\\miauDote.cao\\admin\\configMySQL.ini");
    	if(!file.exists()) {
    		file = new File("C:\\Users\\Joao Gabriel\\Desktop\\backend\\MiauDoteCao\\admin\\configMySQL.ini");
    	}
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
         *Class.forName("com.mysql.jdbc.Driver"); MySQL antigo
         * */
        Class.forName("com.mysql.cj.jdbc.Driver"); //Utilizar esta linha para conectar ao MySQL

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

	public ArrayList<UserOng> getOngName() throws SQLException, ClassNotFoundException, IOException {
		String sql = "SELECT ongName, idOng FROM userOng";
		ArrayList<UserOng> ongs = new ArrayList<UserOng>();
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				UserOng ong = new UserOng();
				ong.setOngName(rs.getString("ongName"));
				ong.setIdOng(rs.getString("idOng"));
				ongs.add(ong);
			}
			if(ongs.isEmpty()) {
				return null;
			}else {
			return ongs;
			}
		}
	}

	public ArrayList<Animal> selectAnimals(String race, String size, String hairType, String animalToAnimal, String animalToPerson,
	        String sex, String age, String id, String city, String state) throws SQLException {
	    StringBuilder sqlBuilder = new StringBuilder();
	    sqlBuilder.append("SELECT a.idAnimal, a.race, a.animalName, a.size, a.hairType, a.animalToAnimal, a.animalToPerson, ");
	    sqlBuilder.append("a.sex, a.age, a.idOng, a.color, a.animalDescription, img.imageUrl ");
	    sqlBuilder.append("FROM animal AS a ");
	    sqlBuilder.append("JOIN userOng AS u ON a.idOng = u.idOng ");
	    sqlBuilder.append("JOIN adress AS ad ON u.idAdress = ad.idAdress ");
	    sqlBuilder.append("LEFT JOIN ( ");
	    sqlBuilder.append("    SELECT idAnimal, imageUrl ");
	    sqlBuilder.append("    FROM image ");
	    sqlBuilder.append("    GROUP BY idAnimal, imageUrl ");
	    sqlBuilder.append("    LIMIT 1 ");
	    sqlBuilder.append(") AS img ON a.idAnimal = img.idAnimal ");
	    sqlBuilder.append("WHERE ad.city = ? ");
	    sqlBuilder.append("  AND ad.state = ? ");
	    ArrayList<String> parameters = new ArrayList<>();
	    parameters.add(city);
	    parameters.add(state);

	    if (race != null && !race.isEmpty() && !race.equals("0")) {
	        sqlBuilder.append("  AND a.race = ? ");
	        parameters.add(race);
	    }
	    if (size != null && !size.isEmpty() && !size.equals("0")) {
	        sqlBuilder.append("  AND a.size = ? ");
	        parameters.add(size);
	    }
	    if (hairType != null && !hairType.isEmpty() && !hairType.equals("0")) {
	        sqlBuilder.append("  AND a.hairType = ? ");
	        parameters.add(hairType);
	    }
	    if (animalToAnimal != null && !animalToAnimal.isEmpty() && !animalToAnimal.equals("0")) {
	        sqlBuilder.append("  AND a.animalToAnimal = ? ");
	        parameters.add(animalToAnimal);
	    }
	    if (animalToPerson != null && !animalToPerson.isEmpty() && !animalToPerson.equals("0")) {
	        sqlBuilder.append("  AND a.animalToPerson = ? ");
	        parameters.add(animalToPerson);
	    }
	    if (sex != null && !sex.isEmpty() && !sex.equals("0")) {
	        sqlBuilder.append("  AND a.sex = ? ");
	        parameters.add(sex);
	    }
	    if (age != null && !age.isEmpty() && !age.equals("0")) {
	        sqlBuilder.append("  AND a.age = ? ");
	        parameters.add(age);
	    }

	    sqlBuilder.append("GROUP BY a.idAnimal, a.race, a.animalName, a.size, a.hairType, a.animalToAnimal, a.animalToPerson, ");
	    sqlBuilder.append("         a.sex, a.age, a.idOng, a.color, a.animalDescription, img.imageUrl ");
	    sqlBuilder.append("ORDER BY a.insertionDate ASC ");

	    String sql = sqlBuilder.toString();

	    ArrayList<Animal> animals = new ArrayList<>();

	    try (Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)) {
	        int parameterIndex = 1;
	        for (String parameter : parameters) {
	            statement.setString(parameterIndex++, parameter);
	        }

	        ResultSet rs = statement.executeQuery();

	        while (rs.next()) {
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
	            a.setColor(rs.getString("color"));
	            a.setAnimalDescription(rs.getString("animalDescription"));
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

	public int insertAnimal(Animal animal) {
		String sql = "INSERT INTO animal (race, animalName, size, hairType, animalToAnimal, animalToPerson, sex, age, idOng, insertionDate, color, animalDescription) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
			statement.setString(1, animal.getRace());
			statement.setString(2, animal.getName());
			statement.setString(3, animal.getSize());
			statement.setString(4, animal.getHairType());
			statement.setString(5, animal.getAnimalToAnimal());
			statement.setString(6, animal.getAnimalToPerson());
			statement.setString(7, animal.getSex());
			statement.setString(8, animal.getAge());
			statement.setString(9, animal.getIdOng());
			statement.setString(10, animal.getInsertionDate());
			statement.setString(11, animal.getColor());
			statement.setString(12, animal.getAnimalDescription());
			int update = statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				int id = keys.getInt(1);
				return id;
			}else {
				throw new SQLException("Nenhuma chave primaria foi gerada.");
			}
		}catch(SQLException | ClassNotFoundException | IOException e) {
			System.err.println("Erro durante o registro: "+e.getMessage());
			return -1;
			}
		}

	public int insertImages(Animal animal, int idAnimal, int i) {
		String sql = "INSERT INTO image (idAnimal, imageUrl) VALUES (?,?)";
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
			statement.setInt(1, idAnimal);
			statement.setString(2, animal.getLinks().get(i));
			int update = statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				int id = keys.getInt(1);
				return id;
			}else {
				throw new SQLException("Nenhuma chave primaria foi gerada.");
			}
		}catch(SQLException | ClassNotFoundException | IOException e) {
			System.err.println("Erro durante o registro: "+e.getMessage());
			return -1;
			}
		}

	public ArrayList<String> getAnimalImages(String idAnimal) throws ClassNotFoundException, IOException{
		String sql = "SELECT imageUrl FROM image WHERE idAnimal=?";
		ArrayList<String> imagens = new ArrayList<String>();
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)){
			statement.setString(1, idAnimal);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				imagens.add(rs.getString(1));
			}
			return imagens;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updateAnimal(Animal newAnimal) throws SQLException, ClassNotFoundException, IOException {
	    StringBuilder sqlBuilder = new StringBuilder();
	    Animal oldAnimal = getAnimalData(newAnimal);
	    sqlBuilder.append("UPDATE animal SET ");

	    ArrayList<String> parameters = new ArrayList<>();

	    if (!oldAnimal.getRace().equals(newAnimal.getRace())) {
	        sqlBuilder.append("race = ?, ");
	        parameters.add(newAnimal.getRace());
	    }

	    if (!oldAnimal.getName().equals(newAnimal.getName())) {
	        sqlBuilder.append("animalName = ?, ");
	        parameters.add(newAnimal.getName());
	    }

	    if (!oldAnimal.getSize().equals(newAnimal.getSize())) {
	        sqlBuilder.append("size = ?, ");
	        parameters.add(newAnimal.getSize());
	    }

	    if (!oldAnimal.getHairType().equals(newAnimal.getHairType())) {
	        sqlBuilder.append("hairType = ?, ");
	        parameters.add(newAnimal.getHairType());
	    }

	    if (!oldAnimal.getAnimalToAnimal().equals(newAnimal.getAnimalToAnimal())) {
	        sqlBuilder.append("animalToAnimal = ?, ");
	        parameters.add(newAnimal.getAnimalToAnimal());
	    }

	    if (!oldAnimal.getAnimalToPerson().equals(newAnimal.getAnimalToPerson())) {
	        sqlBuilder.append("animalToPerson = ?, ");
	        parameters.add(newAnimal.getAnimalToPerson());
	    }

	    if (!oldAnimal.getSex().equals(newAnimal.getSex())) {
	        sqlBuilder.append("sex = ?, ");
	        parameters.add(newAnimal.getSex());
	    }

	    if (!oldAnimal.getAge().equals(newAnimal.getAge())) {
	        sqlBuilder.append("age = ?, ");
	        parameters.add(newAnimal.getAge());
	    }
	    
	    if(!oldAnimal.getColor().equals(newAnimal.getColor())) {
	    	sqlBuilder.append("color = ?, ");
	    	parameters.add(newAnimal.getColor());
	    }
	    
	    if(!oldAnimal.getAnimalDescription().equals(newAnimal.getAnimalDescription())) {
	    	sqlBuilder.append("animalDescription = ?, ");
	    	parameters.add(newAnimal.getAnimalDescription());
	    }
	    sqlBuilder.setLength(sqlBuilder.length() - 2);
	    sqlBuilder.append(" WHERE idAnimal = ?");
	    if(!parameters.isEmpty()) {
	    parameters.add(oldAnimal.getId());

	    String sql = sqlBuilder.toString();

	    try (Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)) {
	        int parameterIndex = 1;
	        for (String parameter : parameters) {
	            statement.setString(parameterIndex++, parameter);
	        }
	        int result = statement.executeUpdate();
	        return result;
	    } catch (ClassNotFoundException | IOException e) {
	        e.printStackTrace();
	        return 0;
	    	}
	    }else {
	    	return 0;
	    }
	}


	private Animal getAnimalData(Animal animal) throws IOException, ClassNotFoundException {
		String sql = "SELECT * FROM animal WHERE idAnimal=?";
		try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)){
			statement.setString(1, animal.getId());
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				Animal a = new Animal();
				a.setName(rs.getString("animalName"));
				a.setRace(rs.getString("race"));
				a.setSize(rs.getString("size"));
				a.setHairType(rs.getString("hairType"));
				a.setAnimalToAnimal(rs.getString("animalToAnimal"));
				a.setAnimalToPerson(rs.getString("animalToPerson"));
				a.setSex(rs.getString("sex"));
				a.setAge(rs.getString("age"));
				a.setId(rs.getString("idAnimal"));
				a.setColor(rs.getString("color"));
				a.setAnimalDescription(rs.getString("animalDescription"));
				return a;
			}else {
				return null;
			}
		}catch(SQLException e) {
			return null;
		}
	}

	public int updateAnimalImages(Animal animal) throws ClassNotFoundException, IOException {
	    ArrayList<String> oldImages = getAnimalImages(animal.getId());
	    Animal animal2 = getAnimalData(animal);
	    List<String> newImages = animal.getLinks();

	    List<String> imagesToAdd = new ArrayList<>(newImages);

	    imagesToAdd.removeAll(oldImages);

	    List<String> imagesToRemove = new ArrayList<>(oldImages);

	    imagesToRemove.retainAll(newImages);

	    StringBuilder sqlBuilder = new StringBuilder();
	    sqlBuilder.append("INSERT INTO image (idAnimal, imageUrl) VALUES ");
	    for (int i = 0; i < imagesToAdd.size(); i++) {
	        sqlBuilder.append("(?, ?), ");
	    }
	    sqlBuilder.setLength(sqlBuilder.length() - 2);
	    String insertSql = sqlBuilder.toString();

	    sqlBuilder = new StringBuilder();
	    sqlBuilder.append("DELETE FROM image WHERE idAnimal = ? AND imageUrl IN (");
	    for (int i = 0; i < imagesToRemove.size(); i++) {
	        sqlBuilder.append("?, ");
	    }
	    sqlBuilder.setLength(sqlBuilder.length() - 2);
	    sqlBuilder.append(")");
	    String deleteSql = sqlBuilder.toString();

	    try (Connection conn = this.connectDB();
	         PreparedStatement insertStatement = conn.prepareStatement(insertSql);
	         PreparedStatement deleteStatement = conn.prepareStatement(deleteSql)) {
	    	int insertResult = 0;
	    	int deleteResult = 0;
	        int parameterIndex = 1;
	        for (String imageUrl : imagesToAdd) {
	            insertStatement.setString(parameterIndex++, animal.getId());
	            insertStatement.setString(parameterIndex++, imageUrl);
	        }

	        deleteStatement.setString(1, animal.getId());
	        for (int i = 0; i < imagesToRemove.size(); i++) {
	            deleteStatement.setString(i + 2, imagesToRemove.get(i));
	        }

	        if (!imagesToAdd.isEmpty()) {
	             insertResult = insertStatement.executeUpdate();
	        }
	        if (!imagesToRemove.isEmpty()) {
	             deleteResult = deleteStatement.executeUpdate();
	        }
	        if(insertResult > 0 && deleteResult > 0) {
	        	return 1;
	        }else {
	        	return 0;
	        }
	    } catch (ClassNotFoundException | IOException | SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}

	public ArrayList<Animal> getAllAnimais() throws ClassNotFoundException, IOException {
		String sql = "SELECT a.idAnimal, a.race, a.animalName, a.size, a.hairType, a.animalToAnimal, a.animalToPerson, a.sex, a.age, a.idOng, a.insertionDate, a.color, a.animalDescription, MAX(i.imageUrl) AS imageUrl "
	             + "FROM animal a "
	             + "LEFT JOIN image i "
	             + "ON a.idAnimal = i.idAnimal "
	             + "GROUP BY a.idAnimal";
	ArrayList<Animal> animals = new ArrayList<Animal>();
	try(Connection conn = this.connectDB(); PreparedStatement statement = conn.prepareStatement(sql)){
	    ResultSet rs = statement.executeQuery();
	    while(rs.next()) {
	        Animal a = new Animal();
	        a.setRace(rs.getString("race"));
	        a.setName(rs.getString("animalName"));
	        a.setSize(rs.getString("size"));
	        a.setHairType(rs.getString("hairType"));
	        a.setAnimalToAnimal(rs.getString("animalToAnimal"));
	        a.setAnimalToPerson(rs.getString("animalToPerson"));
	        a.setSex(rs.getString("sex"));
	        a.setAge(rs.getString("age"));
	        a.setIdOng(rs.getString("idOng"));
	        a.setInsertionDate(rs.getString("insertionDate"));
	        a.setImageUrl(rs.getString("imageUrl"));
	        a.setId(rs.getString("idAnimal"));
	        a.setColor(rs.getString("color"));
	        a.setAnimalDescription(rs.getString("animalDescription"));
	        animals.add(a);
	    }
	    return animals;
	}catch(SQLException e) {
	    e.printStackTrace();
	    return null;
		}
	}
}