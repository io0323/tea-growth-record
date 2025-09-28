import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  Button,
  Card,
  CardContent,
  CardActions,
  Typography,
  Grid,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Alert,
} from '@mui/material';
import { Add as AddIcon, Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { plantApi } from '../services/api';
import type { Plant } from '../types';

/**
 * 植物一覧ページコンポーネント
 */
export const PlantListPage = () => {
  const navigate = useNavigate();
  const [plants, setPlants] = useState<Plant[]>([]);
  const [error, setError] = useState<string>('');
  const [openDialog, setOpenDialog] = useState(false);
  const [editingPlant, setEditingPlant] = useState<Plant | null>(null);
  const [formData, setFormData] = useState<Omit<Plant, 'id' | 'userId'>>({
    name: '',
    species: '',
    plantingDate: '',
    location: '',
    notes: '',
  });

  useEffect(() => {
    loadPlants();
  }, []);

  const loadPlants = async () => {
    try {
      const response = await plantApi.list();
      setPlants(response.data);
    } catch (err) {
      setError('植物一覧の読み込みに失敗しました。');
    }
  };

  const handleOpenDialog = (plant?: Plant) => {
    if (plant) {
      setEditingPlant(plant);
      setFormData({
        name: plant.name,
        species: plant.species,
        plantingDate: plant.plantingDate,
        location: plant.location,
        notes: plant.notes || '',
      });
    } else {
      setEditingPlant(null);
      setFormData({
        name: '',
        species: '',
        plantingDate: '',
        location: '',
        notes: '',
      });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setEditingPlant(null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingPlant) {
        await plantApi.update(editingPlant.id, formData);
      } else {
        await plantApi.create(formData);
      }
      handleCloseDialog();
      loadPlants();
    } catch (err) {
      setError('保存に失敗しました。');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('この植物を削除してもよろしいですか？')) {
      try {
        await plantApi.delete(id);
        loadPlants();
      } catch (err) {
        setError('削除に失敗しました。');
      }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
        <Typography variant="h4" component="h1">
          植物一覧
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => handleOpenDialog()}
        >
          新規登録
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <Grid container spacing={3}>
        {plants.map((plant) => (
          <Grid item xs={12} sm={6} md={4} key={plant.id}>
            <Card>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {plant.name}
                </Typography>
                <Typography color="text.secondary" gutterBottom>
                  品種: {plant.species}
                </Typography>
                <Typography variant="body2">
                  植え付け日: {new Date(plant.plantingDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2">
                  場所: {plant.location}
                </Typography>
                {plant.notes && (
                  <Typography variant="body2" sx={{ mt: 1 }}>
                    メモ: {plant.notes}
                  </Typography>
                )}
              </CardContent>
              <CardActions>
                <IconButton
                  size="small"
                  onClick={() => handleOpenDialog(plant)}
                >
                  <EditIcon />
                </IconButton>
                <IconButton
                  size="small"
                  onClick={() => handleDelete(plant.id)}
                >
                  <DeleteIcon />
                </IconButton>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>
          {editingPlant ? '植物の編集' : '新規植物の登録'}
        </DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <TextField
              fullWidth
              label="名前"
              name="name"
              value={formData.name}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="品種"
              name="species"
              value={formData.species}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="植え付け日"
              name="plantingDate"
              type="date"
              value={formData.plantingDate}
              onChange={handleChange}
              margin="normal"
              required
              InputLabelProps={{ shrink: true }}
            />
            <TextField
              fullWidth
              label="場所"
              name="location"
              value={formData.location}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="メモ"
              name="notes"
              value={formData.notes}
              onChange={handleChange}
              margin="normal"
              multiline
              rows={4}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseDialog}>キャンセル</Button>
            <Button type="submit" variant="contained">
              {editingPlant ? '更新' : '登録'}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </Box>
  );
}; 