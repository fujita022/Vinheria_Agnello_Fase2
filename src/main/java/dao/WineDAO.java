package dao;

import model.Wine;
import utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WineDAO {

    public void insertWine(Wine wine) {

        String sql = "INSERT INTO WINE (name, description, price, image) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wine.getName());
            stmt.setString(2, wine.getDescription());
            stmt.setDouble(3, wine.getPrice());
            stmt.setString(4, wine.getImage());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir vinho", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM WINE WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Wine findById(int id) {
        String sql = "SELECT * FROM WINE WHERE id = ?";
        Wine wine = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    wine = new Wine();
                    wine.setId(rs.getInt("id"));
                    wine.setName(rs.getString("name"));
                    wine.setDescription(rs.getString("description"));
                    wine.setPrice(rs.getDouble("price"));
                    wine.setImage(rs.getString("image"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vinho por ID: " + e.getMessage());
        }

        return wine;
    }

    public List<Wine> list() {

        List<Wine> list = new ArrayList<>();

        String sql = "SELECT * FROM WINE";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Wine wine = new Wine();

                wine.setId(rs.getInt("id"));
                wine.setName(rs.getString("name"));
                wine.setDescription(rs.getString("description"));
                wine.setPrice(rs.getDouble("price"));
                wine.setImage(rs.getString("image"));

                list.add(wine);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vinhos", e);
        }

        return list;
    }

    public void update(Wine wine) {

        String sql = "UPDATE WINE SET name = ?, description = ?, price = ?, image = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wine.getName());
            stmt.setString(2, wine.getDescription());
            stmt.setDouble(3, wine.getPrice());
            stmt.setString(4, wine.getImage());
            stmt.setInt(5, wine.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vinho", e);
        }
    }

}
