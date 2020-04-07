﻿using System;
using UnityEngine;
using UnityEngine.UI;

public class HexGrid : MonoBehaviour
{

    public int width = 3;
    public int height = 3;
    public HexCell cellPrefab;
    public Text cellLabelPrefab;

    Canvas gridCanvas;

    HexCell[] cells;

    HexMesh hexMesh;

    #region Controller
    void Update()
    {
        if (Input.GetMouseButton(0))
        {
            HandleInput();
        }
    }

    void HandleInput()
    {
        Ray inputRay = Camera.main.ScreenPointToRay(Input.mousePosition);
        RaycastHit hit;
        if (Physics.Raycast(inputRay, out hit))
        {
            TouchCell(hit.point);
            FindNeighborTile(hit.point);
        }
    }

    Tuple<HexCell, HexCell> FindNeighborTile(Vector3 position)
    {
        HexCoordinates currentTile = TouchCell(position);
        int currentPos = (int)(currentTile.FromAxialToOffset().x + currentTile.FromAxialToOffset().y*width);
        HexCell[] neighbors = cells[currentPos].GetAllNeighbors();
        float minDistance = 100000f;
        HexCell closestNeighbor = null;
        foreach (HexCell neighbor in neighbors)
        {
            if (neighbor != null) {
                float currDistance = (float)Vector3.Distance(position, neighbor.coordinates.FromCordsToPosition());
                if (currDistance < minDistance)
                {
                    minDistance = currDistance;
                    closestNeighbor = neighbor;
                }
            }
        }
        //Debug.Log((int)(closestNeighbor.coordinates.FromAxialToOffset().x + closestNeighbor.coordinates.FromAxialToOffset().y * width));
        //Debug.Log(minDistance);
        Tuple<HexCell, HexCell> adjacentRoadTiles = new Tuple<HexCell, HexCell>(cells[(int)currentTile.FromAxialToOffset().x+(int)currentTile.FromAxialToOffset().y], closestNeighbor);
        return adjacentRoadTiles;

    }

    HexCoordinates TouchCell(Vector3 position)
    {
        position = transform.InverseTransformPoint(position);
        HexCoordinates coordinates = HexCoordinates.FromPosition(position);
        //Debug.Log(position.ToString());
        //Debug.Log("touched at " + coordinates.ToStringCube());
        return coordinates;
    }
    #endregion
    void Awake()
    {
        gridCanvas = GetComponentInChildren<Canvas>();
        hexMesh = GetComponentInChildren<HexMesh>();
        cells = new HexCell[height * width];

        for (int z = 0, i = 0; z < height; z++)
        {
            for (int x = 0; x < width; x++)
            {
                CreateCell(x, z, i++);
            }
        }
    }

    void CreateCell(int x, int z, int i)
    {
        Vector3 position;
        position.x = (x + z * 0.5f - z / 2) * (HexMetrics.innerRadius * 2f);
        position.y = 0f;
        position.z = z * (HexMetrics.outerRadius * 1.5f);

        HexCell cell = cells[i] = Instantiate<HexCell>(cellPrefab);
        cell.transform.SetParent(transform, false);
        cell.transform.localPosition = position;
        cell.coordinates = HexCoordinates.FromOffsetCoordinates(x, z);

        if (x > 0)
        {
            cell.SetNeighbor(HexDirection.W, cells[i - 1]);
        }
        if (z > 0)
        {
            if ((z & 1) == 0)
            {
                cell.SetNeighbor(HexDirection.SE, cells[i - width]);
                if (x > 0)
                {
                    cell.SetNeighbor(HexDirection.SW, cells[i - width - 1]);
                }
            }
            else
            {
                cell.SetNeighbor(HexDirection.SW, cells[i - width]);
                if (x < width - 1)
                {
                    cell.SetNeighbor(HexDirection.SE, cells[i - width + 1]);
                }
            }
        }

        Text label = Instantiate<Text>(cellLabelPrefab);
        label.rectTransform.SetParent(gridCanvas.transform, false);
        label.rectTransform.anchoredPosition =
            new Vector2(position.x, position.z);
        label.text = cell.coordinates.ToStringCubeOnSeparateLines();
    }
    void Start()
    {
        hexMesh.Triangulate(cells);
    }

}